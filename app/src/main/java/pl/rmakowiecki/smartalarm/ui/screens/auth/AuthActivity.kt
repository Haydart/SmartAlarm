package pl.rmakowiecki.smartalarm.ui.screens.auth

import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_auth.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.mvi.MviActivity
import pl.rmakowiecki.smartalarm.ui.screens.customView.TilingDrawable

class AuthActivity : MviActivity<AuthView, AuthViewState, AuthPresenter>(), AuthView {

    override val shouldMoveToBack: Boolean
        get() = true

    override val layoutRes: Int
        get() = R.layout.activity_auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityBackground()
    }

    private fun setActivityBackground() {
        val rawDrawable = ContextCompat.getDrawable(this, R.drawable.background_vector)

        val tilingDrawable = TilingDrawable(rawDrawable)
        headerBackgroundImage.background = tilingDrawable
    }

    override fun createPresenter() = AuthPresenter()

    override fun emailInputIntent(): Observable<String> =
            RxTextView.textChanges(emailInput).map { it.toString() }

    override fun googleAuthIntent(): Observable<Unit> =
            RxView.clicks(googleButton).map { Unit }

    override fun facebookAuthIntent(): Observable<Unit> =
            RxView.clicks(facebookButton).map { Unit }

    override fun emailSubmitIntent(): Observable<Unit> =
            RxView.clicks(continueButton).map { Unit }

    override fun render(authViewState: AuthViewState) {

    }
}