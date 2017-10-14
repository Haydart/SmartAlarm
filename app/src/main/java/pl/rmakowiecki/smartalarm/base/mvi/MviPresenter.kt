package pl.rmakowiecki.smartalarm.base.mvi

import android.support.annotation.CallSuper
import android.support.annotation.MainThread
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import pl.rmakowiecki.smartalarm.base.Contracts
import java.util.*

abstract class MviPresenter<V : Contracts.View, VS : Contracts.ViewState>(initialViewState: VS) : Contracts.Presenter {

    private var viewStateBehaviorSubject: BehaviorSubject<VS> = BehaviorSubject.createDefault<VS>(initialViewState)
    private var subscribeViewStateMethodCalled = false
    private val intentRelaysBinders = ArrayList<IntentRelayBinderPair<*>>(4)
    private var intentsDisposables = CompositeDisposable()
    private var viewRelayConsumerDisposable = Disposables.disposed()
    private var viewStateDisposable = Disposables.disposed()
    private var viewAttachedFirstTime = true
    private var viewStateConsumer: ((view: V, viewState: VS) -> Unit)? = null

    init {
        reset()
    }

    @CallSuper fun attachView(view: V) {
        if (viewAttachedFirstTime) {
            bindIntents()
        }

        viewStateConsumer?.let {
            subscribeViewStateConsumerActually(view)
        }

        val intentsSize = intentRelaysBinders.size
        (0 until intentsSize)
                .map { intentRelaysBinders[it] }
                .forEach { bindIntentActually(view, it as IntentRelayBinderPair<Any>) }

        viewAttachedFirstTime = false
    }

    protected abstract fun bindIntents()

    @MainThread private fun subscribeViewStateConsumerActually(view: V) {
        viewRelayConsumerDisposable = viewStateBehaviorSubject.subscribe { viewState ->
            viewStateConsumer?.invoke(view, viewState)
        }
    }

    @CallSuper fun detachView(retainInstance: Boolean) {
        if (!retainInstance) {
            viewStateDisposable.dispose()

            unbindIntents()
            reset()
        }

        viewRelayConsumerDisposable.dispose()
        intentsDisposables.dispose()
    }

    protected fun unbindIntents() = Unit

    private fun reset() {
        viewAttachedFirstTime = true
        intentRelaysBinders.clear()
        subscribeViewStateMethodCalled = false
    }

    @MainThread private fun <I> bindIntentActually(view: V, relayBinderPair: IntentRelayBinderPair<I>): Observable<I> {

        val intentRelay = relayBinderPair.intentRelaySubject
        val binderFunction = relayBinderPair.binderFunc
        val intent = binderFunction(view)

        intentsDisposables.add(intent.subscribeWith(DisposableIntentObserver(intentRelay)))
        return intentRelay
    }

    @MainThread protected fun <I> handleIntent(binderFunction: (V) -> Observable<I>): Observable<I> {

        val intentRelay = PublishSubject.create<I>()
        intentRelaysBinders.add(IntentRelayBinderPair(intentRelay, binderFunction))
        return intentRelay
    }

    @MainThread protected fun subscribeViewState(viewStateObservable: Observable<VS>, consumerFunction: (view: V, viewState: VS) -> Unit) {
        if (subscribeViewStateMethodCalled) {
            throw IllegalStateException(
                    "subscribeViewState() method is only allowed to be called once"
            )
        }
        subscribeViewStateMethodCalled = true

        viewStateConsumer = consumerFunction

        viewStateDisposable = viewStateObservable.subscribeWith(
                DisposableViewStateObserver(viewStateBehaviorSubject)
        )
    }

    private inner class IntentRelayBinderPair<I>(
            val intentRelaySubject: PublishSubject<I>,
            val binderFunc: (V) -> Observable<I>
    )
}