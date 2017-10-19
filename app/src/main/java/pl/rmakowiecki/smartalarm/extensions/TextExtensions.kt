package pl.rmakowiecki.smartalarm.extensions

private const val EMAIL_PATTERN = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,63}"

fun String.isValidEmail() = EMAIL_PATTERN.toRegex().matches(this)