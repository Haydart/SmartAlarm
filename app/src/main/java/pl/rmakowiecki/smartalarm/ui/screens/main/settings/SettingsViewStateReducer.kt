package pl.rmakowiecki.smartalarm.ui.screens.main.settings

import pl.rmakowiecki.smartalarm.ui.screens.main.settings.SettingsViewStateChange.*
import javax.inject.Inject

class SettingsViewStateReducer @Inject constructor() {

    fun reduce(currentState: SettingsViewState, change: SettingsViewStateChange) = when (change) {

        is PhotoCountValueChange -> currentState.copy(
                isLoadingPhotoCount = true,
                photosCount = change.newValue
        )
        is SequenceIntervalValueChange -> currentState.copy(
                isLoadingSequenceInterval = true,
                photosSequenceInterval = change.newValue
        )
        is PhotoCountChangeComplete -> currentState.copy(
                isLoadingPhotoCount = false
        )
        is SequenceIntervalChangeComplete -> currentState.copy(
                isLoadingSequenceInterval = false
        )
    }
}