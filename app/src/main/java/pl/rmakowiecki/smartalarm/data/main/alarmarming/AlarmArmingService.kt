package pl.rmakowiecki.smartalarm.data.main.alarmarming

import io.reactivex.Single

interface AlarmArmingService {

    fun updateAlarmState(armed: Boolean): Single<Boolean>
}