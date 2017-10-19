package pl.rmakowiecki.smartalarm.extensions

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.applyIoSchedulers(): Observable<T> =
        subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.applyIoSchedulers(): Single<T> =
        subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.applyComputationSchedulers(): Single<T> =
        subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.applyComputationSchedulers(): Observable<T> =
        subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())

infix fun CompositeDisposable.handle(disposable: Disposable) {
    this += disposable
}