package pl.rmakowiecki.smartalarm.feature.splash

import pl.rmakowiecki.smartalarm.base.Contracts
import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class SplashPresenter @Inject constructor(
        private val interactor: SplashInteractor
) : MviPresenter<Contracts.View, Contracts.ViewState>() {

    override fun bindIntents() = subscribeViewState(interactor.stubintent, null)
}