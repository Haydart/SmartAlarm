package pl.rmakowiecki.smartalarm.data.main.alarmincidents

import pl.rmakowiecki.smartalarm.domain.main.alarmincidents.AlarmTriggerReason

data class SecurityIncident(
        val archived: Boolean = true,
        val reason: AlarmTriggerReason? = null,
        val thumbnailUrl: String = "",
        val timestamp: Long = 0L
)