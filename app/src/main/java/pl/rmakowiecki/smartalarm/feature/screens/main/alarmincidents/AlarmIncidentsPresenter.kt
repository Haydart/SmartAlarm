package pl.rmakowiecki.smartalarm.feature.screens.main.alarmincidents

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class AlarmIncidentsPresenter @Inject constructor(
        private val interactor: AlarmIncidentsInteractor
) : MviPresenter<AlarmIncidentsView, AlarmIncidentsViewState>() {

    override fun bindIntents() = with(interactor) {
        attachArchiveIntent(
                intent(AlarmIncidentsView::incidentArchivingIntent))

        attachDeletionIntent(
                intent(AlarmIncidentsView::incidentDeletionIntent))

        attachDetailsIntent(
                intent(AlarmIncidentsView::incidentDetailsIntent))

        attachSnackBarDismissIntent(
                intent(AlarmIncidentsView::snackBarDismissIntent))

        subscribeViewState(getViewStateObservable(), AlarmIncidentsView::render)
    }
}