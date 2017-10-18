package pl.rmakowiecki.smartalarm.ui.screens.auth

import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_auth.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.mvi.MviActivity
import pl.rmakowiecki.smartalarm.ui.customView.TilingDrawable

class AuthActivity : MviActivity<Auth.View, AuthViewState, AuthPresenter>(),
        Auth.View, Auth.Navigator {

    override val layoutRes = R.layout.activity_auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityBackground()
    }

    private fun setActivityBackground() {
        val rawDrawable = ContextCompat.getDrawable(this, R.drawable.background_vector)

        val tilingDrawable = TilingDrawable(rawDrawable)
        headerBackgroundImage.background = tilingDrawable
    }

    override fun createPresenter() = AuthPresenter(AuthInteractor())

    override fun facebookAuthIntent(): Observable<Unit> =
            RxView.clicks(facebookButton).map { Unit }

    override fun googleAuthIntent(): Observable<Unit> =
            RxView.clicks(googleButton).map { Unit }

    override fun emailInputIntent(): Observable<String> =
            RxTextView.textChanges(emailInput).map { it.toString() }

    override fun passwordInputIntent(): Observable<String> =
            RxTextView.textChanges(emailInput).map { it.toString() }

    override fun repeatPasswordInputIntent(): Observable<String> =
            RxTextView.textChanges(emailInput).map { it.toString() }

    override fun credentialsSubmitIntent(): Observable<Unit> =
            RxView.clicks(continueButton).map { Unit }

    override fun emailRegistrationIntent(): Observable<Unit> =
            RxView.clicks(registerText).map { Unit }

    override fun forgotPasswordIntent(): Observable<Unit> =
            RxView.clicks(forgotPasswordText).map { Unit }

    override fun render(authViewState: AuthViewState) {

    }
}