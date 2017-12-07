package pl.rmakowiecki.smartalarm.domain.auth

data class RegisterCredentials(
        val email: String,
        val password: String,
        val repeatPassword: String
)