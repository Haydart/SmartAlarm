package pl.rmakowiecki.smartalarm.ui.screens.auth

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter

class AuthPresenter(
        private val interactor: Auth.Interactor
) : MviPresenter<Auth.View, AuthViewState>() {

    override fun bindIntents() {
        interactor.harnessUserIntents(
                handleIntent(Auth.View::facebookAuthIntent),
                handleIntent(Auth.View::googleAuthIntent),
                handleIntent(Auth.View::emailInputIntent),
                handleIntent(Auth.View::emailSubmitIntent),
                handleIntent(Auth.View::forgotPasswordIntent)
        )

        subscribeViewState(interactor.mergedIntentStream, Auth.View::render)
    }
}