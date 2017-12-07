package pl.rmakowiecki.smartalarm.feature.screens.main.settings

sealed class SettingsViewStateChange {

    class PhotoCountValueChange(val newValue: Int) : SettingsViewStateChange()
    class SequenceIntervalValueChange(val newValue: Int) : SettingsViewStateChange()
    object PhotoCountChangeComplete : SettingsViewStateChange()
    object SequenceIntervalChangeComplete : SettingsViewStateChange()
}