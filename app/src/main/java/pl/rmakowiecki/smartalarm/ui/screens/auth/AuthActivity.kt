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
import pl.rmakowiecki.smartalarm.extensions.*
import pl.rmakowiecki.smartalarm.ui.customView.TilingDrawable
import pl.rmakowiecki.smartalarm.ui.screens.auth.AuthPerspective.*

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

    private val credentialsButtonClicksObservable
        get() = credentialsSubmitButton.clicks().share()

    override val loginIntent: Observable<LoginCredentials>
        get() = credentialsButtonClicksObservable
                .doOnEach { logD("login intent ") }
                .filter { !loginIntentBlocked }
                .map {
                    LoginCredentials(
                            emailInput.text.toString(),
                            passwordInput.text.toString())
                }

    override val registerIntent: Observable<RegisterCredentials>
        get() = credentialsButtonClicksObservable
                .doOnEach { logD("register intent ") }
                .filter { !registerIntentBlocked }
                .map {
                    RegisterCredentials(
                            emailInput.text.toString(),
                            passwordInput.text.toString(),
                            repeatPasswordInput.text.toString())
                }

    override val resetPasswordIntent: Observable<RemindPasswordCredentials>
        get() = credentialsButtonClicksObservable
                .doOnEach { logD("password reset intent ") }
                .filter { !resetPasswordIntentBlocked }
                .map { RemindPasswordCredentials(emailInput.text.toString()) }

    override val emailRegistrationIntent: Observable<Unit>
        get() = registerText.clicks()

    private val backButtonPublishSubject = PublishSubject.create<Unit>()

    override val backButtonIntent: Observable<Unit>
        get() = backButtonPublishSubject

    override val forgotPasswordIntent: Observable<Unit>
        get() = forgotPasswordText.clicks()

    private var loginIntentBlocked = false
    private var registerIntentBlocked = true
    private var resetPasswordIntentBlocked = true

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
                    CredentialsValidator(),
                    FirebaseAuthService()
            ))

    override fun onBackPressed() = backButtonPublishSubject.onNext(Unit)

    override fun render(authViewState: AuthViewState) = with(authViewState) {

        emailInput.setTextIfDifferent(emailInputText)
        passwordInput.setTextIfDifferent(passwordInputText)
        repeatPasswordInput.setTextIfDifferent(repeatPasswordInputText)

        emailInputLayout.isErrorEnabled = emailInputError.isNotBlank()
        emailInputLayout.error = emailInputError

        passwordInputLayout.isErrorEnabled = passwordInputError.isNotBlank()
        passwordInputLayout.error = passwordInputError

        repeatPasswordInputLayout.isErrorEnabled = repeatPasswordInputError.isNotBlank()
        repeatPasswordInputLayout.error = repeatPasswordInputError

        credentialsSubmitButton.isEnabled = credentialsSubmitButtonEnabled

        when {
            isLoading -> credentialsSubmitButton.showProcessing()
            isShowingSuccess -> credentialsSubmitButton.showSuccess()
            generalError.isNotBlank() -> credentialsSubmitButton.showFailure(generalError)
        }

        when (screenPerspective) {
            LOGIN -> showLoginPerspective()
            REGISTER -> showRegisterPerspective()
            FORGOT_PASSWORD -> showForgotPasswordPerspective()
        }
    }

    private fun showLoginPerspective() {
        facebookButton.visible()
        googleButton.visible()
        passwordInputLayout.visible()
        repeatPasswordInputLayout.gone()

        registerText.visible()
        forgotPasswordText.visible()

        credentialsSubmitButton.setText(getString(R.string.login))

        loginIntentBlocked = false
        registerIntentBlocked = true
        resetPasswordIntentBlocked = true
    }

    private fun showRegisterPerspective() {
        facebookButton.gone()
        googleButton.gone()
        passwordInputLayout.visible()
        repeatPasswordInputLayout.visible()

        registerText.gone()
        forgotPasswordText.gone()

        credentialsSubmitButton.setText(getString(R.string.register))

        loginIntentBlocked = true
        registerIntentBlocked = false
        resetPasswordIntentBlocked = true
    }

    private fun showForgotPasswordPerspective() {
        facebookButton.gone()
        googleButton.gone()
        passwordInputLayout.invisible()
        repeatPasswordInputLayout.gone()

        registerText.gone()
        forgotPasswordText.gone()

        credentialsSubmitButton.setText(getString(R.string.reset_password))

        loginIntentBlocked = true
        registerIntentBlocked = true
        resetPasswordIntentBlocked = false
    }
}