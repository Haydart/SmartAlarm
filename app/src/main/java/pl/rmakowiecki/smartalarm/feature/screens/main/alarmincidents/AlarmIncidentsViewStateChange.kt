package pl.rmakowiecki.smartalarm.feature.screens.main.alarmincidents

sealed class AlarmIncidentsViewStateChange {

    class ItemsEmpty(val isEmpty: Boolean) : AlarmIncidentsViewStateChange()
    class ItemRemoved(val model: SecurityIncidentItemViewState, val positon: Int) : AlarmIncidentsViewStateChange()
    class ItemAdded(val model: SecurityIncidentItemViewState) : AlarmIncidentsViewStateChange()
    class ItemUpdated(val model: SecurityIncidentItemViewState, val positon: Int) : AlarmIncidentsViewStateChange()
    class ItemArchived(val positionInList: Int) : AlarmIncidentsViewStateChange()
    class ItemDeleted(val positionInList: Int) : AlarmIncidentsViewStateChange()
    class SnackBarShown(val message: String) : AlarmIncidentsViewStateChange()
    class SnackBarHidden : AlarmIncidentsViewStateChange()
}