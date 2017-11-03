package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import io.reactivex.Single

interface AlarmHistoryService {

    fun archiveIncident(): Single<Boolean>
    fun deleteIncident(): Single<Boolean>
}