package pl.rmakowiecki.smartalarm.feature.screens.main.alarmarming

import pl.rmakowiecki.smartalarm.feature.screens.main.alarmarming.AlarmArmingViewStateChange.AlarmArmed
import pl.rmakowiecki.smartalarm.feature.screens.main.alarmarming.AlarmArmingViewStateChange.AlarmDisarmed
import javax.inject.Inject

class AlarmArmingInteractor @Inject constructor(
        private val reducer: AlarmArmingViewStateReducer
)

class AlarmArmingViewStateReducer {

    fun reduce(currentViewState: AlarmStateViewState, change: AlarmArmingViewStateChange) = when (change) {

        AlarmArmed -> currentViewState
        AlarmDisarmed -> currentViewState
    }
}

sealed class AlarmArmingViewStateChange {

    object AlarmArmed : AlarmArmingViewStateChange()
    object AlarmDisarmed : AlarmArmingViewStateChange()
}
