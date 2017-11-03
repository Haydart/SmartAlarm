package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import io.reactivex.Single

class FirebaseAlarmHistoryService : AlarmHistoryService {

    override fun archiveIncident(): Single<Boolean> {
        //todo implement
        return Single.just(true)
    }

    override fun deleteIncident(): Single<Boolean> {
        //todo implement
        return Single.just(true)
    }
}