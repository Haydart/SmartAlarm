package pl.rmakowiecki.smartalarm.feature.incidentdetails

sealed class IncidentDetailsViewStateChange {

    class CurrentPhotoChanged(val newPosition: Int) : IncidentDetailsViewStateChange()
    class PhotoAdded(val newPhoto: String) : IncidentDetailsViewStateChange()
}