package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import javax.inject.Inject

class AlarmViewStateReducer @Inject constructor() {

    fun reduce(currentViewState: AlarmHistoryViewState, change: AlarmViewStateChange): AlarmHistoryViewState = when (change) {
        is AlarmViewStateChange.ItemArchived -> {
            currentViewState
        }
        is AlarmViewStateChange.ItemDeleted -> {
            currentViewState
        }
    }
}