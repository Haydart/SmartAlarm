package pl.rmakowiecki.smartalarm.feature.setup

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class SetupPresenter @Inject constructor(
        private val interactor: SetupInteractor
) : MviPresenter<SetupView, SetupViewState>() {

    override fun bindIntents() = with(interactor) {

        attachSsidInputIntent(
                bindIntent(SetupView::ssidInputIntent))

        attachNetworkPasswordInputIntent(
                bindIntent(SetupView::networkPasswordInputIntent))

        attachNetworkCredentialsSubmitIntent(
                bindIntent(SetupView::networkCredentialsSubmitIntent))


        subscribeViewState(viewStateObservable, SetupView::render)
    }
}