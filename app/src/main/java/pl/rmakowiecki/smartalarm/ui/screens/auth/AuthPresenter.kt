package pl.rmakowiecki.smartalarm.ui.screens.auth

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter

class AuthPresenter : MviPresenter<AuthView, AuthViewState>(AuthViewState()) {
    override fun bindIntents() {
        val facebookIntent = handleIntent(AuthView::facebookAuthIntent)
                .map { AuthViewState() }

        val googleIntent = handleIntent(AuthView::googleAuthIntent)
                .map { AuthViewState() }

        val emailInputIntent = handleIntent(AuthView::emailInputIntent)
                .map { AuthViewState() }

        val emailSubmitIntent = handleIntent(AuthView::emailSubmitIntent)
                .map { AuthViewState() }

        val mergedIntentStream = Observable.merge(facebookIntent, googleIntent, emailInputIntent, emailSubmitIntent)

        subscribeViewState(mergedIntentStream, AuthView::render)
    }
}