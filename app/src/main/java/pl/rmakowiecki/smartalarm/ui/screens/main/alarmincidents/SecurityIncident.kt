package pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents

data class SecurityIncident(
        val archived: Boolean = true,
        val reason: AlarmTriggerReason? = null,
        val thumbnailUrl: String = "",
        val timestamp: Long = 0L
)