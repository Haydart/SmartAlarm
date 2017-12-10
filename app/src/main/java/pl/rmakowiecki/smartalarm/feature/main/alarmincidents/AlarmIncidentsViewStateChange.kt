package pl.rmakowiecki.smartalarm.feature.main.alarmincidents

import pl.rmakowiecki.smartalarm.domain.main.alarmincidents.SecurityIncidentItemViewState

sealed class AlarmIncidentsViewStateChange {

    class ItemsEmpty(val isEmpty: Boolean) : AlarmIncidentsViewStateChange()
    class ItemRemoved(val model: SecurityIncidentItemViewState, val positon: Int) : AlarmIncidentsViewStateChange()
    class ItemAdded(val model: SecurityIncidentItemViewState) : AlarmIncidentsViewStateChange()
    class ItemUpdated(val model: SecurityIncidentItemViewState, val positon: Int) : AlarmIncidentsViewStateChange()
    class ItemArchived(val positionInList: Int) : AlarmIncidentsViewStateChange()
    class ItemDeleted(val positionInList: Int) : AlarmIncidentsViewStateChange()
    class SnackBarShown(val message: String) : AlarmIncidentsViewStateChange()
    object SnackBarHidden : AlarmIncidentsViewStateChange()
}