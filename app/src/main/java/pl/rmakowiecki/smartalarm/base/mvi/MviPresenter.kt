package pl.rmakowiecki.smartalarm.base.mvi

import android.support.annotation.CallSuper
import android.support.annotation.MainThread
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import pl.rmakowiecki.smartalarm.base.Contracts
import java.util.*

abstract class MviPresenter<V : Contracts.View, VS : Contracts.ViewState> : Contracts.Presenter {

    private var viewStateRenderBehaviorSubject: BehaviorSubject<VS> = BehaviorSubject.create()
    private var viewStateRenderConsumer: ((V, VS) -> Unit)? = null

    private val intentRelaysBinders = ArrayList<IntentRelayBinderPair<*>>()

    private var viewIntentsDisposables = CompositeDisposable()
    private var viewRenderRelayDisposable = Disposables.disposed()
    private var viewStateDisposable = Disposables.disposed()
    private var strayDisposables = CompositeDisposable()

    private var subscribeViewStateMethodCalled = false

    private var viewAttachedForTheFirstTime = true

    @CallSuper
    fun attachView(view: V) {
        if (viewAttachedForTheFirstTime) {
            bindIntents()
        }

        performViewStateSubscription(view)

        intentRelaysBinders.forEach { bindViewStateIntentActually(view, it) }

        viewAttachedForTheFirstTime = false
    }

    protected abstract fun bindIntents()

    @MainThread private fun performViewStateSubscription(view: V) {
        viewRenderRelayDisposable = viewStateRenderBehaviorSubject
                .subscribe { viewState ->
                    viewStateRenderConsumer?.invoke(view, viewState)
                }
    }

    @CallSuper
    fun detachView() {
        viewRenderRelayDisposable.dispose()
        viewIntentsDisposables.clear()
    }

    protected fun unbindIntents() {
        viewStateDisposable.dispose()
        strayDisposables.clear()
    }

    @MainThread private fun <I> bindViewStateIntentActually(view: V, relayBinderPair: IntentRelayBinderPair<I>) {
        val intentRelay = relayBinderPair.intentRelaySubject
        val intentObservable = relayBinderPair.binderFunction(view)

        viewIntentsDisposables.add(intentObservable
                .subscribeWith(DisposableIntentObserver(intentRelay))
        )
    }

    @MainThread protected fun <I> intent(binderFunction: (V) -> Observable<I>): Observable<I> {
        val intentRelay = PublishSubject.create<I>()
        intentRelaysBinders.add(IntentRelayBinderPair(intentRelay, binderFunction))
        return intentRelay
    }

    @MainThread protected fun subscribeViewState(viewStateObservable: Observable<out VS>, consumerFunction: ((view: V, viewState: VS) -> Unit)? = null) {
        if (subscribeViewStateMethodCalled) {
            throw IllegalStateException(
                    "subscribeViewState() method is only allowed to be called once"
            )
        }
        subscribeViewStateMethodCalled = true

        viewStateRenderConsumer = consumerFunction

        viewStateDisposable = viewStateObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(DisposableViewStateObserver(viewStateRenderBehaviorSubject))
    }

    private inner class IntentRelayBinderPair<I>(
            val intentRelaySubject: PublishSubject<I>,
            val binderFunction: (V) -> Observable<I>
    )
}