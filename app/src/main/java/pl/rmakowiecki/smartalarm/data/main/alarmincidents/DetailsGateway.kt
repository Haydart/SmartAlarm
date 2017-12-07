package pl.rmakowiecki.smartalarm.data.main.alarmincidents

import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailsGateway @Inject constructor() {

    var incidentBackendIdSingle: Single<String> = Single.just("")
}