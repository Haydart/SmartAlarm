package pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class AlarmIncidentsPresenter @Inject constructor(
        private val interactor: AlarmIncidentsInteractor
) : MviPresenter<AlarmIncidents.View, AlarmIncidentsViewState>() {

    override fun bindIntents() = with(interactor) {

        attachArchiveIntent(
                bindIntent(AlarmIncidents.View::incidentArchivingIntent)
        )
        attachDeletionIntent(
                bindIntent(AlarmIncidents.View::incidentDeletionIntent)
        )
        attachDetailsIntent(
                bindIntent(AlarmIncidents.View::incidentDetailsIntent)
        )
        attachSnackBarDismissIntent(
                bindIntent(AlarmIncidents.View::snackBarDismissIntent)
        )

        subscribeViewState(getViewStateObservable(), AlarmIncidents.View::render)
    }
}