package pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents

import pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents.AlarmIncidentsViewStateChange.*
import javax.inject.Inject

class AlarmIncidentsViewStateReducer @Inject constructor() {

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
            val newList = currentViewState.incidentsList.toMutableList().apply { removeAt(change.positon) }

            currentViewState.copy(
                    isLoading = false,
                    incidentsList = newList,
                    isPlaceholderVisible = newList.isEmpty()
            )
        }
        is ItemUpdated -> {
            currentViewState.copy(
                    isLoading = false,
                    incidentsList = currentViewState.incidentsList.toMutableList().apply {
                        this[change.positon] = change.model
                    },
                    isPlaceholderVisible = false
            )
        }
        is SnackBarShown -> {
            currentViewState.copy(isSnackBarShown = true, snackBarMessage = change.message)
        }
        is SnackBarHidden -> {
            currentViewState.copy(isSnackBarShown = false, snackBarMessage = null)
        }
    }
}