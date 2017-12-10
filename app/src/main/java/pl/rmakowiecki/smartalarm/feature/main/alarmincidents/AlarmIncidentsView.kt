package pl.rmakowiecki.smartalarm.feature.main.alarmincidents

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface AlarmIncidentsView : Contracts.View {

    val incidentArchivingIntent: Observable<Int>
    val incidentDeletionIntent: Observable<Int>
    val incidentDetailsIntent: Observable<Int>
    val snackBarDismissIntent: Observable<Unit>

    fun render(viewState: AlarmIncidentsViewState)
}