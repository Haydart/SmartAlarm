package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

sealed class AlarmViewStateChange {

    class ItemArchived(val positionInList: Int) : AlarmViewStateChange()
    class ItemDeleted(val positionInList: Int) : AlarmViewStateChange()
}