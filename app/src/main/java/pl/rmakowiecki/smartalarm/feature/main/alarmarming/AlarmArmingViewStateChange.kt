package pl.rmakowiecki.smartalarm.feature.main.alarmarming

sealed class AlarmArmingViewStateChange {

    object AlarmArmed : AlarmArmingViewStateChange()
    object AlarmDisarmed : AlarmArmingViewStateChange()
}