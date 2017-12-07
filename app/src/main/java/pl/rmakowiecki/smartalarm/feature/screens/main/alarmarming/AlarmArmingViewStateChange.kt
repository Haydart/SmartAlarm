package pl.rmakowiecki.smartalarm.feature.screens.main.alarmarming

sealed class AlarmArmingViewStateChange {

    object AlarmArmed : AlarmArmingViewStateChange()
    object AlarmDisarmed : AlarmArmingViewStateChange()
}