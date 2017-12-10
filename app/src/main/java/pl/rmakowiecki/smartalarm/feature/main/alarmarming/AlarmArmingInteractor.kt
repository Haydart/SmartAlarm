package pl.rmakowiecki.smartalarm.feature.main.alarmarming

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.feature.main.alarmarming.AlarmArmingViewStateChange.AlarmArmed
import pl.rmakowiecki.smartalarm.feature.main.alarmarming.AlarmArmingViewStateChange.AlarmDisarmed
import javax.inject.Inject

class AlarmArmingInteractor @Inject constructor(
        private val reducer: AlarmArmingViewStateReducer
) {

    private var viewStateChanges = Observable.empty<AlarmArmingViewStateChange>()

    val viewStateObservable: Observable<AlarmArmingViewState>
        get() = viewStateChanges
                .scan(AlarmArmingViewState(), reducer::reduce)

    fun attachAlarmArmingIntent(intentObservable: Observable<Unit>) = mergeChanges(
            intentObservable.map { AlarmArmed }
    )

    fun attachAlarmDisarmingIntent(intentObservable: Observable<Unit>) = mergeChanges(
            intentObservable.map { AlarmDisarmed }
    )

    private fun <T : AlarmArmingViewStateChange> mergeChanges(vararg changes: Observable<out T>) = changes.forEach {
        viewStateChanges = viewStateChanges.mergeWith(it)
    }
}