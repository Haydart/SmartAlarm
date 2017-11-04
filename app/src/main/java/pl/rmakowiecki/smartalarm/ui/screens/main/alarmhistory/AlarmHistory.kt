package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface AlarmHistory {

    interface View : Contracts.View {
        val incidentArchivingIntent: Observable<SecurityIncident>
        val incidentDeletionIntent: Observable<SecurityIncident>
        val incidentDetailsIntent: Observable<SecurityIncident>

        fun render(viewState: AlarmHistoryViewState)
    }

    interface Interactor : Contracts.Interactor {
        fun getViewStateObservable(): Observable<AlarmHistoryViewState>

        fun attachArchiveIntent(intentObservable: Observable<SecurityIncident>)
        fun attachDeletionIntent(intentObservable: Observable<SecurityIncident>)
        fun attachDetailsIntent(intentObservable: Observable<SecurityIncident>)
    }

    interface Navigator : Contracts.Navigator {
        fun showIncidentDetailsScreen()
    }
}