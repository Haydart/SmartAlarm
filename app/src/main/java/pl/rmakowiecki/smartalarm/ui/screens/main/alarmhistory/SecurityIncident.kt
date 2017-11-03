package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

class SecurityIncident(
        val archived: Boolean,
        val reason: AlarmTriggerReason,
        val thumbnailUrl: String,
        val timestamp: Long
)