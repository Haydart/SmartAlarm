package pl.rmakowiecki.smartalarm.feature.screens.main.alarmarming

import pl.rmakowiecki.smartalarm.feature.screens.main.alarmarming.AlarmArmingViewStateChange.AlarmArmed
import pl.rmakowiecki.smartalarm.feature.screens.main.alarmarming.AlarmArmingViewStateChange.AlarmDisarmed
import javax.inject.Inject

class AlarmArmingViewStateReducer @Inject constructor() {

    fun reduce(currentViewState: AlarmArmingViewState, change: AlarmArmingViewStateChange) = when (change) {

        AlarmArmed -> currentViewState
        AlarmDisarmed -> currentViewState
    }
}