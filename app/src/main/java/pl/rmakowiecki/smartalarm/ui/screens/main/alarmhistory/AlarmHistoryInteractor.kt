package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.extensions.logD
import javax.inject.Inject

class AlarmHistoryInteractor @Inject constructor() : AlarmHistory.Interactor {

    private var viewStateObservable = Observable.empty<AlarmHistoryViewState>()

    override fun getViewStateObservable(): Observable<AlarmHistoryViewState> = viewStateObservable

    override fun attachArchiveIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable.mergeWith(
                intentObservable.doOnEach { logD("archiving $it") }.map { AlarmHistoryViewState() }
        )
    }

    override fun attachDeletionIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable.mergeWith(
                intentObservable.doOnEach { logD("deleting $it") }.map { AlarmHistoryViewState() }
        )
    }

    override fun attachDetailsIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable.mergeWith(
                intentObservable.doOnEach { logD("details of $it") }.map { AlarmHistoryViewState() }
        )
    }
}