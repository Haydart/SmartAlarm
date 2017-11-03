package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface AlarmHistory {

    interface View : Contracts.View {
        val incidentArchivingIntent: Observable<Int>
        val incidentDeletionIntent: Observable<Int>

        fun render(viewState: AlarmHistoryViewState)
    }

    interface Interactor : Contracts.Interactor {
        fun getViewStateObservable(): Observable<AlarmHistoryViewState>

        fun attachArchiveIntent(intentObservable: Observable<Int>)
        fun attachDeletionIntent(intentObservable: Observable<Int>)
    }
}