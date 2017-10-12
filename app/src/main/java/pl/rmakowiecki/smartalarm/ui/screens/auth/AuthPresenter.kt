package pl.rmakowiecki.smartalarm.ui.screens.auth

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter

class AuthPresenter : MviPresenter<AuthView, AuthViewState>(AuthViewState.Idle()) {
    override fun bindIntents() {
        val facebookIntent = handleIntent(AuthView::facebookAuthIntent)
        val googleIntent = handleIntent(AuthView::googleAuthIntent)
        val emailInputIntent = handleIntent(AuthView::emailInputIntent)
        val emailSubmitIntent = handleIntent(AuthView::emailSubmitIntent)

        val mergedIntentStream = Observable.merge(facebookIntent, googleIntent, emailInputIntent, emailSubmitIntent)

        subscribeViewState(mergedIntentStream, AuthView::render)
    }
}