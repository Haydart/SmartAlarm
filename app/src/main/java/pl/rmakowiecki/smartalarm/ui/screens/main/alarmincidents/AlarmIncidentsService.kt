package pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents

import io.reactivex.Observable
import io.reactivex.Single

interface AlarmIncidentsService {

    fun isIncidentsListEmpty(): Single<Boolean>
    fun observeIncidentsChanges(): Observable<IncidentChange>
    fun archiveIncident(listPosition: Int): Single<Boolean>
    fun deleteIncident(listPosition: Int): Single<Boolean>
    fun fetchIdForListPosition(listPosition: Int): Single<String>
}