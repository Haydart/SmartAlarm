package pl.rmakowiecki.smartalarm.feature.screens.main.alarmarming

import io.reactivex.Observable
import javax.inject.Inject

class AlarmArmingInteractor @Inject constructor(
        private val reducer: AlarmArmingViewStateReducer
) {

    private var viewStateChanges = Observable.empty<AlarmArmingViewStateChange>()

    val viewStateObservable: Observable<AlarmArmingViewState>
        get() = viewStateChanges
                .scan(AlarmArmingViewState(), reducer::reduce)

    fun attachAlarmArmingIntent(intent: Observable<Unit>) = mergeChanges(
            intent.map { AlarmArmingViewStateChange.AlarmArmed }
    )

    fun attachAlarmDisarmingIntent(intent: Observable<Unit>) = mergeChanges(
            intent.map { AlarmArmingViewStateChange.AlarmDisarmed }
    )

    private fun <T : AlarmArmingViewStateChange> mergeChanges(changes: Observable<T>) {
        viewStateChanges = viewStateChanges.mergeWith(changes)
    }
}