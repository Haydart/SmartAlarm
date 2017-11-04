package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import io.reactivex.Single

interface AlarmHistoryService {

    fun archiveIncident(securityIncident: SecurityIncident): Single<Boolean>
    fun deleteIncident(securityIncident: SecurityIncident): Single<Boolean>
}