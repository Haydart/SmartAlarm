package pl.rmakowiecki.smartalarm.feature.screens.setup

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class SetupPresenter @Inject constructor(
        private val interactor: SetupInteractor
) : MviPresenter<SetupView, SetupViewState>() {

    override fun bindIntents() = with(interactor) {

        attachSsidInputIntent(
                intent(SetupView::ssidInputIntent))

        attachNetworkPasswordInputIntent(
                intent(SetupView::networkPasswordInputIntent))

        attachNetworkCredentialsSubmitIntent(
                intent(SetupView::networkCredentialsSubmitIntent))


        subscribeViewState(viewStateObservable, SetupView::render)
    }
}