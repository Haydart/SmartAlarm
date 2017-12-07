package pl.rmakowiecki.smartalarm.feature.screens.main.alarmarming

import pl.rmakowiecki.smartalarm.feature.screens.main.alarmarming.AlarmArmingViewStateChange.AlarmArmed
import pl.rmakowiecki.smartalarm.feature.screens.main.alarmarming.AlarmArmingViewStateChange.AlarmDisarmed

class AlarmArmingViewStateReducer {

    fun reduce(currentViewState: AlarmArmingViewState, change: AlarmArmingViewStateChange) = when (change) {

        AlarmArmed -> currentViewState
        AlarmDisarmed -> currentViewState
    }
}