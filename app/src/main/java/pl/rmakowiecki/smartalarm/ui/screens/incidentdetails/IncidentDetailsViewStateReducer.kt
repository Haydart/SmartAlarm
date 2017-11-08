package pl.rmakowiecki.smartalarm.ui.screens.incidentdetails

import javax.inject.Inject

class IncidentDetailsViewStateReducer @Inject constructor() {

    fun reduce(currentState: IncidentDetailsViewState, change: IncidentDetailsViewStateChange) = when (change) {
        is IncidentDetailsViewStateChange.CurrentPhotoChanged -> {
            currentState.copy(currentPhotoPage = change.newPosition)
        }
        is IncidentDetailsViewStateChange.PhotoAdded -> {
            currentState.copy(photoUrlsList = currentState
                    .photoUrlsList
                    .toMutableList()
                    .plus(change.newPhoto)
            )
        }
    }
}