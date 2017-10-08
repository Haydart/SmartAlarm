package pl.rmakowiecki.smartalarm.ui.screens.auth

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.mvi.MviActivity


class AuthActivity : MviActivity<AuthView, AuthViewState, AuthPresenter>(), AuthView {

    override val layoutRes: Int
        get() = R.layout.activity_auth

    override fun createPresenter() = AuthPresenter()

    override fun render(authViewState: AuthViewState) {
        //todo implement
    }

    override fun emailInputIntent(): Observable<String> {
        //todo implement
    }

    override fun googleAuthIntent(): Observable<Unit> {
        //todo implement
    }

    override fun facebookAuthIntent(): Observable<Unit> {
        //todo implement
    }

    override fun emailSignInIntent(): Observable<Unit> {
        //todo implement
    }
}
