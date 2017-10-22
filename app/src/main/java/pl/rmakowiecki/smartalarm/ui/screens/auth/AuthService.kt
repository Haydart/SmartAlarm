package pl.rmakowiecki.smartalarm.ui.screens.auth

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Single

class FirebaseAuthService : AuthService {

    override fun login(credentials: LoginCredentials): Single<AuthResponse> = Single.create { emitter ->
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(credentials.email, credentials.password)
                .addOnCompleteListener {
                    emitter.onSuccess(AuthResponse(it.isSuccessful, it.exception))
                }
    }

    override fun register(credentials: RegisterCredentials): Single<AuthResponse> = Single.create { emitter ->
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(credentials.email, credentials.password)
                .addOnCompleteListener {
                    emitter.onSuccess(AuthResponse(it.isSuccessful, it.exception))
                }
    }

    override fun resetPassword(credentials: RemindPasswordCredentials): Single<AuthResponse> = Single.create { emitter ->
        FirebaseAuth.getInstance()
                .sendPasswordResetEmail(credentials.email)
                .addOnCompleteListener {
                    emitter.onSuccess(AuthResponse(it.isSuccessful, it.exception))
                }
    }

    override fun signInWithFacebook(credential: AuthCredential): Single<AuthResponse> {
        //todo implement
        return Single.just(AuthResponse(false, Throwable()))
    }

    override fun signInWithGoogle(credential: AuthCredential): Single<AuthResponse> {
        //todo implement
        return Single.just(AuthResponse(false, Throwable()))
    }
}

interface AuthService {
    fun login(credentials: LoginCredentials): Single<AuthResponse>
    fun register(credentials: RegisterCredentials): Single<AuthResponse>
    fun resetPassword(credentials: RemindPasswordCredentials): Single<AuthResponse>
    fun signInWithFacebook(credential: AuthCredential): Single<AuthResponse>
    fun signInWithGoogle(credential: AuthCredential): Single<AuthResponse>
}

class AuthResponse(
        val isSuccessful: Boolean,
        val error: Throwable?
)