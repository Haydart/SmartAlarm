package pl.rmakowiecki.smartalarm.ui.screens.auth

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Single

class FirebaseAuthService : AuthService {

    override fun login(credentials: LoginCredentials): Single<Boolean> = Single.create { emitter ->
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(credentials.email, credentials.password)
                .addOnCompleteListener { emitter.onSuccess(it.isSuccessful) }
    }

    override fun register(credentials: RegisterCredentials): Single<Boolean> = Single.create { emitter ->
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(credentials.email, credentials.password)
                .addOnCompleteListener { emitter.onSuccess(it.isSuccessful) }
    }

    override fun remindPassword(credentials: RemindPasswordCredentials): Single<Boolean> = Single.create { emitter ->
        FirebaseAuth.getInstance()
                .sendPasswordResetEmail(credentials.email)
                .addOnCompleteListener { emitter.onSuccess(it.isSuccessful) }
    }

    override fun signInWithFacebook(credential: AuthCredential) {
        //todo implement
    }

    override fun signInWithGoogle(credential: AuthCredential) {
        //todo implement
    }
}

interface AuthService {
    fun login(credentials: LoginCredentials): Single<Boolean>
    fun register(credentials: RegisterCredentials): Single<Boolean>
    fun remindPassword(credentials: RemindPasswordCredentials): Single<Boolean>
    fun signInWithFacebook(credential: AuthCredential)
    fun signInWithGoogle(credential: AuthCredential)
}