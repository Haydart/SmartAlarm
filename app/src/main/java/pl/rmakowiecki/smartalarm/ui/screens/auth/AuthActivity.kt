package pl.rmakowiecki.smartalarm.ui.screens.auth

import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_auth.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.mvi.MviActivity
import pl.rmakowiecki.smartalarm.extensions.gone
import pl.rmakowiecki.smartalarm.extensions.logD
import pl.rmakowiecki.smartalarm.extensions.setTextIfDifferent
import pl.rmakowiecki.smartalarm.extensions.visible
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
        get() = registerText.clicks().doOnEach { logD("email register button click") }

    private val backButtonPublishSubject = PublishSubject.create<Unit>()

    override val backButtonIntent: Observable<Unit>
        get() = backButtonPublishSubject.doOnEach { logD("back button click") }

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

    override fun createPresenter() = AuthPresenter(
            AuthInteractor(
                    this,
                    AuthStateReducer(),
                    CredentialsValidator()
            ))

    override fun onBackPressed() = backButtonPublishSubject.onNext(Unit)

    override fun render(authViewState: AuthViewState) = with(authViewState) {
        emailInput.setTextIfDifferent(emailInputText)
        passwordInput.setTextIfDifferent(passwordInputText)
        repeatPasswordInput.setTextIfDifferent(repeatPasswordInputText)
        credentialsSubmitButton.isEnabled = credentialsSubmitButtonEnabled

        emailInputLayout.isErrorEnabled = emailInputError.isNotBlank()
        emailInputLayout.error = emailInputError

        passwordInputLayout.isErrorEnabled = passwordInputError.isNotBlank()
        passwordInputLayout.error = passwordInputError

        repeatPasswordInputLayout.isErrorEnabled = repeatPasswordInputError.isNotBlank()
        repeatPasswordInputLayout.error = repeatPasswordInputError

        if (screenPerspective == AuthPerspective.LOGIN) {
            facebookButton.visible()
            googleButton.visible()
            repeatPasswordInputLayout.gone()

            registerText.visible()
            forgotPasswordText.visible()

            credentialsSubmitButton.setText(getString(R.string.login))
        } else {
            facebookButton.gone()
            googleButton.gone()
            repeatPasswordInputLayout.visible()

            registerText.gone()
            forgotPasswordText.gone()

            credentialsSubmitButton.setText(getString(R.string.register))
        }
    }
}