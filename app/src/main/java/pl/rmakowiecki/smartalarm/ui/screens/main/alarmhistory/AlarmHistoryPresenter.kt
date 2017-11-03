package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class AlarmHistoryPresenter @Inject constructor(
        private val interactor: AlarmHistoryInteractor
) : MviPresenter<AlarmHistory.View, AlarmHistoryViewState>() {

    override fun bindIntents() = with(interactor) {

        attachArchiveIntent(bindIntent(AlarmHistory.View::incidentArchivingIntent))
        attachDeletionIntent(bindIntent(AlarmHistory.View::incidentDeletionIntent))
        attachDetailsIntent(bindIntent(AlarmHistory.View::incidentDetailsIntent))

        subscribeViewState(getViewStateObservable(), AlarmHistory.View::render)
    }
}