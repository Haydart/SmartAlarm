package pl.rmakowiecki.smartalarm.feature.screens.incidentdetails

import pl.rmakowiecki.smartalarm.feature.screens.incidentdetails.IncidentDetailsViewStateChange.CurrentPhotoChanged
import pl.rmakowiecki.smartalarm.feature.screens.incidentdetails.IncidentDetailsViewStateChange.PhotoAdded
import javax.inject.Inject

class IncidentDetailsViewStateReducer @Inject constructor() {

    fun reduce(currentState: IncidentDetailsViewState, change: IncidentDetailsViewStateChange) = when (change) {

        is CurrentPhotoChanged -> currentState.copy(
                currentPhotoPage = change.newPosition
        )
        is PhotoAdded -> currentState.copy(
                photoUrlsList = currentState
                        .photoUrlsList
                        .toMutableList()
                        .plus(change.newPhoto)
        )
    }
}