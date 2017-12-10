package pl.rmakowiecki.smartalarm.feature.main.alarmarming

import pl.rmakowiecki.smartalarm.feature.main.alarmarming.AlarmArmingViewStateChange.AlarmArmed
import pl.rmakowiecki.smartalarm.feature.main.alarmarming.AlarmArmingViewStateChange.AlarmDisarmed
import javax.inject.Inject

class AlarmArmingViewStateReducer @Inject constructor() {

    fun reduce(currentViewState: AlarmArmingViewState, change: AlarmArmingViewStateChange) = when (change) {

        AlarmArmed -> currentViewState.copy(
                isInitializing = false,
                isAlarmArmed = true
        )
        AlarmDisarmed -> currentViewState.copy(
                isInitializing = false,
                isAlarmArmed = false
        )
    }
}