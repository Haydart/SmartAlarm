package pl.rmakowiecki.smartalarm.feature.screens.main.alarmarming

import pl.rmakowiecki.smartalarm.base.Contracts
import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class AlarmArmingPresenter @Inject constructor(
        private val interactor: AlarmArmingInteractor
) : MviPresenter<AlarmStateView, Contracts.ViewState>() {

    override fun bindIntents() = Unit
}