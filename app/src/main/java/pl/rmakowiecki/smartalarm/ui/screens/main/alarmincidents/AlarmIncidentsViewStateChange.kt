package pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents

sealed class AlarmIncidentsViewStateChange {

    class ItemArchived(val positionInList: Int) : AlarmIncidentsViewStateChange()
    class ItemDeleted(val positionInList: Int) : AlarmIncidentsViewStateChange()
}