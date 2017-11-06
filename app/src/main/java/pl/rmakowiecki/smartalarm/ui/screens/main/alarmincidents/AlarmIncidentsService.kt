package pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents

import io.reactivex.Observable
import io.reactivex.Single

interface AlarmIncidentsService {

    fun isIncidentsListEmpty(): Single<Boolean>
    fun observeIncidentsChanges(): Observable<List<SecurityIncident>>
    fun archiveIncident(listPosition: Int): Single<Boolean>
    fun deleteIncident(listPosition: Int): Single<Boolean>
}