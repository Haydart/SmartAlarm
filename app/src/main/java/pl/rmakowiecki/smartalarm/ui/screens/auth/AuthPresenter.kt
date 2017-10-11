package pl.rmakowiecki.smartalarm.ui.screens.auth

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter

class AuthPresenter : MviPresenter<AuthView, AuthViewState>(AuthViewState.Idle()) {
    override fun bindIntents() {
        val observable = handleIntent(object : ViewIntentBinder<AuthView, Unit> {
            override fun bind(view: AuthView): Observable<Unit> =
                    view.googleAuthIntent()
        })

        subscribeViewState(observable, object : ViewStateConsumer<AuthView, >)
    }
}