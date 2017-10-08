package pl.rmakowiecki.smartalarm.base.mvi

import android.support.annotation.CallSuper
import android.support.annotation.MainThread
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import pl.rmakowiecki.smartalarm.base.Contracts
import java.util.*

abstract class MviPresenter<V : MvpView, VS : ViewState>(initialViewState: VS) : Contracts.Presenter {

    /**
     * This relay is the bridge to the viewState (UI). Whenever the viewState get's reattached, the
     * latest
     * state will be reemitted.
     */
    private var viewStateBehaviorSubject: BehaviorSubject<VS> = BehaviorSubject.createDefault<VS>(initialViewState)

    /**
     * We only allow to cal [.subscribeViewState] method once
     */
    private var subscribeViewStateMethodCalled = false
    /**
     * List of internal relays, bridging the gap between intents coming from the viewState (will be
     * unsubscribed temporarily when viewState is detached i.e. during config changes)
     */
    private val intentRelaysBinders = ArrayList<IntentRelayBinderPair<*>>(4)

    /**
     * Composite Disposals holding subscriptions to all intents observable offered by the viewState.
     */
    private var intentsDisposables = CompositeDisposable()

    /**
     * Disposal to unsubscribe from the viewState when the viewState is detached (i.e. during screen
     * orientation
     * changes)
     */
    private var viewRelayConsumerDisposable = Disposables.disposed()

    /**
     * Disposable between the viewState observable returned from [.handleIntent]
     * and
     * [.viewStateBehaviorSubject]
     */
    private var viewStateDisposable = Disposables.disposed()

    /**
     * Will be used to determine whether or not a View has been attached for the first time.
     * This is used to determine whether or not the intents should be bound via [ ][.bindIntents]
     * or rebound internally.
     */
    private var viewAttachedFirstTime = true

    /**
     * This binder is used to subscribe the view's render method to render the ViewState in the view.
     */
    private var viewStateConsumer: ViewStateVisitor<V, VS>? = null

    init {
        reset()
    }

    @CallSuper fun attachView(view: V) {
        if (viewAttachedFirstTime) {
            bindIntents()
        }

        //
        // Build the chain from bottom to top:
        // 1. Subscribe to ViewState
        // 2. Subscribe intents
        //
        if (viewStateConsumer != null) {
            subscribeViewStateConsumerActually(view)
        }

        val intentsSize = intentRelaysBinders.size
        (0..intentsSize - 1)
                .map { intentRelaysBinders[it] }
                .forEach { bindIntentActually<Any>(view, it) }

        viewAttachedFirstTime = false
    }

    protected abstract fun bindIntents()

    @MainThread private fun subscribeViewStateConsumerActually(view: V) {
        viewRelayConsumerDisposable = viewStateBehaviorSubject.subscribe { vs -> viewStateConsumer?.accept(view, vs) }
    }

    @CallSuper fun detachView(retainInstance: Boolean) {
        if (!retainInstance) {
            // Cancel the overall observable stream
            viewStateDisposable.dispose()

            unbindIntents()
            reset()
        }

        // Cancel subscription from View to viewState Relay
        viewRelayConsumerDisposable.dispose()

        // Cancel subscriptons from view intents to handleIntent Relays
        intentsDisposables.dispose()
    }

    protected fun unbindIntents() = Unit

    private fun reset() {
        viewAttachedFirstTime = true
        intentRelaysBinders.clear()
        subscribeViewStateMethodCalled = false
    }

    @MainThread private fun <I> bindIntentActually(view: V, relayBinderPair: IntentRelayBinderPair<*>): Observable<I> {

        val intentRelay = relayBinderPair.intentRelaySubject as PublishSubject<I>
        val intentBinder = relayBinderPair.intentBinder as ViewIntentBinder<V, I>
        val intent = intentBinder.bind(view)

        intentsDisposables.add(intent.subscribeWith(DisposableIntentObserver(intentRelay)))
        return intentRelay
    }

    @MainThread protected fun <I> handleIntent(binder: ViewIntentBinder<V, I>): Observable<I> {
        val intentRelay = PublishSubject.create<I>()
        intentRelaysBinders.add(IntentRelayBinderPair(intentRelay, binder))
        return intentRelay
    }

    @MainThread protected fun subscribeViewState(viewStateObservable: Observable<VS>, consumer: ViewStateVisitor<V, VS>) {
        if (subscribeViewStateMethodCalled) {
            throw IllegalStateException(
                    "subscribeViewState() method is only allowed to be called once"
            )
        }
        subscribeViewStateMethodCalled = true

        this.viewStateConsumer = consumer

        viewStateDisposable = viewStateObservable.subscribeWith(
                DisposableViewStateObserver(viewStateBehaviorSubject)
        )
    }

    private inner class IntentRelayBinderPair<I>(
            val intentRelaySubject: PublishSubject<I>,
            val intentBinder: ViewIntentBinder<V, I>
    )

    protected interface ViewStateVisitor<in V : MvpView, in VS> {
        fun accept(view: V, viewState: VS)
    }

    protected interface ViewIntentBinder<in V : MvpView, I> {
        fun bind(view: V): Observable<I>
    }
}