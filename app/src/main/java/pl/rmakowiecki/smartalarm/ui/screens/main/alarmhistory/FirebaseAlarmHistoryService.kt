package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import io.reactivex.Single
import javax.inject.Inject

class FirebaseAlarmHistoryService @Inject constructor() : AlarmHistoryService {

    override fun archiveIncident(): Single<Boolean> =//todo implement
            Single.just(true)

    override fun deleteIncident(): Single<Boolean> =//todo implement
            Single.just(true)
}