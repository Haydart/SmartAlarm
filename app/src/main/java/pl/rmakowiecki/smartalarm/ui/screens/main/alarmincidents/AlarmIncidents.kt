package pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents

import io.reactivex.Maybe
import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface AlarmIncidents {

    interface View : Contracts.View {
        val incidentArchivingIntent: Observable<Int>
        val incidentDeletionIntent: Observable<Int>
        val incidentDetailsIntent: Observable<Int>

        fun render(viewState: AlarmIncidentsViewState)
    }

    interface Interactor : Contracts.Interactor {
        fun getViewStateObservable(): Observable<AlarmIncidentsViewState>

        fun attachArchiveIntent(intentObservable: Observable<Int>)
        fun attachDeletionIntent(intentObservable: Observable<Int>)
        fun attachDetailsIntent(intentObservable: Observable<Int>)
    }

    interface Navigator : Contracts.Navigator {
        fun showIncidentDetailsScreen()
        fun showDeleteConfirmationDialog(): Maybe<Boolean>
    }
}