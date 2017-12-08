package pl.rmakowiecki.smartalarm.domain.auth

data class LoginCredentials(
        val email: String,
        val password: String
)