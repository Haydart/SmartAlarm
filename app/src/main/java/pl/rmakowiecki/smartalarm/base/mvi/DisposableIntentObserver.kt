package pl.rmakowiecki.smartalarm.base.mvi

import io.reactivex.observers.DisposableObserver
import io.reactivex.subjects.PublishSubject

internal class DisposableIntentObserver<I>(private val subject: PublishSubject<I>) : DisposableObserver<I>() {

    override fun onNext(value: I) = subject.onNext(value)

    override fun onError(throwable: Throwable) = throw IllegalStateException(
            "View itnent observable must not reach error state - onError()", throwable
    )

    override fun onComplete() = subject.onComplete()
}