package pl.rmakowiecki.smartalarm.base.mvi

import io.reactivex.observers.DisposableObserver
import io.reactivex.subjects.BehaviorSubject

internal class DisposableViewStateObserver<VS>(private val subject: BehaviorSubject<VS>) : DisposableObserver<VS>() {

    override fun onNext(viewState: VS) = subject.onNext(viewState)

    override fun onError(throwable: Throwable) = throw IllegalStateException(
            "ViewState observable must not reach error state - onError() $throwable", throwable
    )

    override fun onComplete() = Unit
}