package pl.rmakowiecki.smartalarm.ui.screens.auth

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthService @Inject constructor() : AuthService {

    override fun isUserLoggedIn(): Single<Boolean> = Single.just(
            FirebaseAuth.getInstance().currentUser != null
    )

    override fun login(credentials: LoginCredentials): Single<AuthResponse> = Single.create { emitter ->
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(credentials.email, credentials.password)
                .addOnCompleteListener {
                    FirebaseMessaging.getInstance().subscribeToTopic("SgVIHNDQwsPj3lmS2jS1gS9Xz5r1") //todo pass on-hardcoded core device UID
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

    override fun logoutUser(): Single<Boolean> {
        FirebaseAuth.getInstance().signOut()
        FirebaseMessaging.getInstance().unsubscribeFromTopic("SgVIHNDQwsPj3lmS2jS1gS9Xz5r1") //todo pass on-hardcoded core device UID

        return Single.just(true)
    }
}

interface AuthService {
    fun login(credentials: LoginCredentials): Single<AuthResponse>
    fun register(credentials: RegisterCredentials): Single<AuthResponse>
    fun resetPassword(credentials: RemindPasswordCredentials): Single<AuthResponse>
    fun signInWithFacebook(credential: AuthCredential): Single<AuthResponse>
    fun signInWithGoogle(credential: AuthCredential): Single<AuthResponse>
    fun isUserLoggedIn(): Single<Boolean>
    fun logoutUser(): Single<Boolean>
}

class AuthResponse(
        val isSuccessful: Boolean,
        val error: Throwable?
)