package pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents

import pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents.AlarmIncidentsViewStateChange.*
import javax.inject.Inject

class AlarmViewStateReducer @Inject constructor() {

    fun reduce(currentViewState: AlarmIncidentsViewState, change: AlarmIncidentsViewStateChange): AlarmIncidentsViewState = when (change) {
        is ItemArchived -> {
            currentViewState
        }
        is ItemDeleted -> {
            currentViewState
        }
        is ItemsEmpty -> {
            if (change.isEmpty) {
                currentViewState.copy(isLoading = false, isPlaceholderVisible = true, incidentsList = emptyList())
            } else currentViewState
        }
        is ItemAdded -> {
            currentViewState.copy(
                    isLoading = false,
                    incidentsList = currentViewState.incidentsList.toMutableList().apply {
                        add(change.model)
                    },
                    isPlaceholderVisible = false
            )
        }
        is ItemRemoved -> {
            currentViewState.copy(
                    isLoading = false,
                    incidentsList = currentViewState.incidentsList.toMutableList().apply {
                        removeAt(change.positon)
                    }
            )
        }
        is AlarmIncidentsViewStateChange.ItemUpdated -> {
            currentViewState.copy(
                    isLoading = false,
                    incidentsList = currentViewState.incidentsList.toMutableList().apply {
                        this[change.positon] = change.model
                    },
                    isPlaceholderVisible = false
            )
        }
    }
}