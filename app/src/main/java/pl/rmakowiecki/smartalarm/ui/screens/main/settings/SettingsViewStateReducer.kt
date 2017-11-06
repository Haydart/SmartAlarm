package pl.rmakowiecki.smartalarm.ui.screens.main.settings

import javax.inject.Inject

class SettingsViewStateReducer @Inject constructor() {

    fun reduce(currentState: SettingsViewState, change: SettingsViewStateChange) = when (change) {

        is SettingsViewStateChange.PhotoCountValueChange -> {
            currentState
        }
        is SettingsViewStateChange.SequenceIntervalValueChange -> {
            currentState
        }
        is SettingsViewStateChange.PhotoCountChangeComplete -> {
            currentState
        }
        is SettingsViewStateChange.SequenceIntervalChangeComplete -> {
            currentState
        }
    }
}