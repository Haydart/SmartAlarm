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
import pl.rmakowiecki.smartalarm.extensions.invisible
import pl.rmakowiecki.smartalarm.extensions.setTextIfDifferent
import pl.rmakowiecki.smartalarm.extensions.visible
import pl.rmakowiecki.smartalarm.ui.customView.TilingDrawable
import pl.rmakowiecki.smartalarm.ui.screens.auth.AuthPerspective.*
import javax.inject.Inject

class AuthActivity : MviActivity<Auth.View, AuthViewState, AuthPresenter>(),
        Auth.View {

    @Inject lateinit var presenter: AuthPresenter

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

    override val loginIntent: Observable<LoginCredentials>
        get() = loginButton.clicks()
                .filter { !loginIntentBlocked }
                .map {
                    LoginCredentials(
                            emailInput.text.toString(),
                            passwordInput.text.toString())
                }

    override val registerIntent: Observable<RegisterCredentials>
        get() = registerButton.clicks()
                .filter { !registerIntentBlocked }
                .map {
                    RegisterCredentials(
                            emailInput.text.toString(),
                            passwordInput.text.toString(),
                            repeatPasswordInput.text.toString())
                }

    override val resetPasswordIntent: Observable<RemindPasswordCredentials>
        get() = resetPasswordButton.clicks()
                .filter { !resetPasswordIntentBlocked }
                .map { RemindPasswordCredentials(emailInput.text.toString()) }

    override val emailRegistrationIntent: Observable<Unit>
        get() = registerText.clicks()

    private val backButtonPublishSubject = PublishSubject.create<Unit>()

    override val backButtonIntent: Observable<Unit>
        get() = backButtonPublishSubject

    override val forgotPasswordIntent: Observable<Unit>
        get() = forgotPasswordText.clicks()

    override fun retrievePresenter() = presenter

    override fun injectComponents() = activityComponent.inject(this)

    private var loginIntentBlocked = false
    private var registerIntentBlocked = true
    private var resetPasswordIntentBlocked = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityBackground()
    }

    private fun setActivityBackground() {
        val rawDrawable = ContextCompat.getDrawable(this, R.drawable.overlay_pattern_background_vector)

        val tilingDrawable = TilingDrawable(rawDrawable)
        headerBackgroundImage.background = tilingDrawable
    }

    override fun onBackPressed() = backButtonPublishSubject.onNext(Unit)

    override fun render(authViewState: AuthViewState) = with(authViewState) {

        emailInput.setTextIfDifferent(emailInputText)
        passwordInput.setTextIfDifferent(passwordInputText)
        repeatPasswordInput.setTextIfDifferent(repeatPasswordInputText)

        emailInputLayout.isErrorEnabled = emailInputError != null
        emailInputLayout.error = emailInputError ?: ""

        passwordInputLayout.isErrorEnabled = passwordInputError != null
        passwordInputLayout.error = passwordInputError ?: ""

        repeatPasswordInputLayout.isErrorEnabled = repeatPasswordInputError != null
        repeatPasswordInputLayout.error = repeatPasswordInputError ?: ""

        when (screenPerspective) {
            LOGIN -> showLoginPerspective(this)
            REGISTER -> showRegisterPerspective(this)
            FORGOT_PASSWORD -> showForgotPasswordPerspective(this)
        }
    }

    private fun showLoginPerspective(authViewState: AuthViewState) {
        facebookButton.visible()
        googleButton.visible()
        passwordInputLayout.visible()
        repeatPasswordInputLayout.gone()

        loginButton.visible()
        registerButton.invisible()
        resetPasswordButton.invisible()

        registerText.visible()
        forgotPasswordText.visible()

        with(authViewState) {

            loginButton.isEnabled = credentialsSubmitButtonEnabled

            when {
                isLoading -> loginButton.showProcessing()
                isShowingSuccess -> loginButton.showSuccess()
                generalError != null -> loginButton.showFailure(generalError)
            }
        }

        loginIntentBlocked = false
        registerIntentBlocked = true
        resetPasswordIntentBlocked = true
    }

    private fun showRegisterPerspective(authViewState: AuthViewState) {
        facebookButton.gone()
        googleButton.gone()
        passwordInputLayout.visible()
        repeatPasswordInputLayout.visible()

        loginButton.invisible()
        registerButton.visible()
        resetPasswordButton.invisible()

        registerText.gone()
        forgotPasswordText.gone()

        with(authViewState) {
            registerButton.isEnabled = credentialsSubmitButtonEnabled

            when {
                isLoading -> registerButton.showProcessing()
                !isLoading && generalError == null && !isShowingSuccess -> loginButton.showNeutral()
                isShowingSuccess -> registerButton.showSuccess()
                generalError != null -> registerButton.showFailure(generalError)
            }
        }

        loginIntentBlocked = true
        registerIntentBlocked = false
        resetPasswordIntentBlocked = true
    }

    private fun showForgotPasswordPerspective(authViewState: AuthViewState) {
        facebookButton.gone()
        googleButton.gone()
        passwordInputLayout.invisible()
        repeatPasswordInputLayout.gone()

        loginButton.invisible()
        registerButton.invisible()
        resetPasswordButton.visible()

        registerText.gone()
        forgotPasswordText.gone()

        with(authViewState) {
            resetPasswordButton.isEnabled = credentialsSubmitButtonEnabled

            when {
                isLoading -> resetPasswordButton.showProcessing()
                !isLoading && generalError == null && !isShowingSuccess -> loginButton.showNeutral()
                isShowingSuccess -> resetPasswordButton.showSuccess()
                generalError != null -> resetPasswordButton.showFailure(generalError)
            }
        }

        loginIntentBlocked = true
        registerIntentBlocked = true
        resetPasswordIntentBlocked = false
    }
}