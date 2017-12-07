package pl.rmakowiecki.smartalarm.ui.screens.main.settings

sealed class SettingsViewStateChange {

    class PhotoCountValueChange(val newValue: Int) : SettingsViewStateChange()
    class SequenceIntervalValueChange(val newValue: Int) : SettingsViewStateChange()
    class PhotoCountChangeComplete : SettingsViewStateChange()
    class SequenceIntervalChangeComplete : SettingsViewStateChange()
}