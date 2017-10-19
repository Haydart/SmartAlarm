package pl.rmakowiecki.smartalarm.ui.screens.auth

import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_auth.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.mvi.MviActivity
import pl.rmakowiecki.smartalarm.extensions.setTextIfDifferent
import pl.rmakowiecki.smartalarm.ui.customView.TilingDrawable

class AuthActivity : MviActivity<Auth.View, AuthViewState, AuthPresenter>(),
        Auth.View, Auth.Navigator {

    override val layoutRes = R.layout.activity_auth

    override val facebookAuthIntent
        get() = facebookButton.clicks()

    override val googleAuthIntent
        get() = googleButton.clicks()

    override val emailInputIntent: Observable<String>
        get() = emailInput.textChanges().map(CharSequence::toString)

    override val passwordInputIntent: Observable<String>
        get() = passwordInput.textChanges().map(CharSequence::toString)

    override val repeatPasswordInputIntent: Observable<String>
        get() = repeatPasswordInput.textChanges().map(CharSequence::toString)

    override val credentialsSubmitIntent
        get() = credentialsSubmitButton.clicks()

    override val emailRegistrationIntent: Observable<Unit>
        get() = registerText.clicks()

    override val forgotPasswordIntent: Observable<Unit>
        get() = forgotPasswordText.clicks()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityBackground()
    }

    private fun setActivityBackground() {
        val rawDrawable = ContextCompat.getDrawable(this, R.drawable.background_vector)

        val tilingDrawable = TilingDrawable(rawDrawable)
        headerBackgroundImage.background = tilingDrawable
    }

    override fun createPresenter() = AuthPresenter(AuthInteractor(this, AuthStateReducer()))

    override fun render(authViewState: AuthViewState) = with(authViewState) {
        emailInput.setTextIfDifferent(emailInputText)
        passwordInput.setTextIfDifferent(passwordInputText)
        repeatPasswordInput.setTextIfDifferent(repeatPasswordInputText)
        credentialsSubmitButton.isEnabled = credentialsSubmitButtonEnabled

        emailInputLayout.isErrorEnabled = emailInputError.isNotBlank()
        emailInputLayout.error = emailInputError
    }
}