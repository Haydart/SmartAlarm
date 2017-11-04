package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import io.reactivex.Observable
import io.reactivex.Single

interface AlarmHistoryService {

    fun registerForChanges(): Observable<List<SecurityIncident>>
    fun archiveIncident(listPosition: Int): Single<Boolean>
    fun deleteIncident(listPosition: Int): Single<Boolean>
}