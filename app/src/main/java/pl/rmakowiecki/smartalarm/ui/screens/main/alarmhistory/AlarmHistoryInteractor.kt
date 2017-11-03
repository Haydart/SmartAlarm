package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import io.reactivex.Observable
import javax.inject.Inject

class AlarmHistoryInteractor @Inject constructor() : AlarmHistory.Interactor {

    private var viewStateObservable = Observable.empty<AlarmHistoryViewState>()

    override fun getViewStateObservable(): Observable<AlarmHistoryViewState> = viewStateObservable

    override fun attachArchiveIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable.mergeWith(
                intentObservable.map { AlarmHistoryViewState() }
        )
    }

    override fun attachDeletionIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable.mergeWith(
                intentObservable.map { AlarmHistoryViewState() }
        )
    }
}