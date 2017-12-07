package pl.rmakowiecki.smartalarm.feature.screens.main.alarmstate

import pl.rmakowiecki.smartalarm.base.Contracts
import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class AlarmStatePresenter @Inject constructor(
        private val interactor: AlarmStateInteractor
) : MviPresenter<AlarmStateView, Contracts.ViewState>() {

    override fun bindIntents() = Unit
}