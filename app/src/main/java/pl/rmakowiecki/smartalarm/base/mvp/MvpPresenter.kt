package pl.rmakowiecki.smartalarm.base.mvp

import android.os.Handler
import android.os.Looper
import io.reactivex.disposables.CompositeDisposable
import pl.rmakowiecki.smartalarm.base.Contracts

abstract class MvpPresenter<V : Contracts.View, out RO : Contracts.Router>(private val router: RO? = null)
    : Contracts.Presenter {

    protected val disposables = CompositeDisposable()
    private val uiCommands = mutableSetOf<(V) -> Unit>()
    private var view: V? = null
    private val uiHandler: Handler = Handler(Looper.getMainLooper())

    fun attachView(view: V) {
        this.view = view
        invokeQueuedUiActions(view)
        onViewAttached()
    }

    open fun onViewAttached() = Unit

    protected fun performUiTransition(function: (RO) -> Unit) {
        router?.let {
            function(it)
        } ?: throw IllegalStateException("No router injected but ordered to perform a transition")
    }

    protected fun performUiAction(function: (V) -> Unit) {
        view?.let {
            uiHandler.post { function(it) }
        } ?: queueUiAction(function)
    }

    private fun queueUiAction(function: (V) -> Unit) {
        uiCommands += function
    }

    private fun invokeQueuedUiActions(view: V) {
        uiCommands.forEach { it(view) }
        uiCommands.clear()
    }

    fun detachView() {
        this.view = null
    }

    fun onViewDestroy() = disposables.clear()
}