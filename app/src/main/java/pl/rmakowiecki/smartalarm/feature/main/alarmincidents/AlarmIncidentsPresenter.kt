package pl.rmakowiecki.smartalarm.feature.main.alarmincidents

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class AlarmIncidentsPresenter @Inject constructor(
        private val interactor: AlarmIncidentsInteractor
) : MviPresenter<AlarmIncidentsView, AlarmIncidentsViewState>() {

    override fun bindIntents() = with(interactor) {
        attachArchiveIntent(
                bindIntent(AlarmIncidentsView::incidentArchivingIntent))

        attachDeletionIntent(
                bindIntent(AlarmIncidentsView::incidentDeletionIntent))

        attachDetailsIntent(
                bindIntent(AlarmIncidentsView::incidentDetailsIntent))

        attachSnackBarDismissIntent(
                bindIntent(AlarmIncidentsView::snackBarDismissIntent))

        subscribeViewState(viewStateObservable, AlarmIncidentsView::render)
    }
}