package pl.rmakowiecki.smartalarm.domain.main.alarmincidents

enum class AlarmTriggerReason {
    BEAM_BREAK_DETECTOR {
        override fun toString() = "Beam break detector"
    },
    MOTION_SENSOR {
        override fun toString() = "Motion sensor"
    },
}