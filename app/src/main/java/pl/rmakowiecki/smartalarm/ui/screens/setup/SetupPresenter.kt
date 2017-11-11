package pl.rmakowiecki.smartalarm.ui.screens.setup

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class SetupPresenter @Inject constructor(
        private val interactor: SetupInteractor
) : MviPresenter<Setup.View, SetupViewState>() {

    override fun bindIntents() = with(interactor) {

        attachSsidInputIntent(bindIntent(Setup.View::ssidInputIntent))
        attachNetworkPasswordInputIntent(bindIntent(Setup.View::networkPasswordInputIntent))
        attachNetworkCredentialsSubmitIntent(bindIntent(Setup.View::networkCredentialsSubmitIntent))

        subscribeViewState(getViewStateObservable(), Setup.View::render)
    }
}