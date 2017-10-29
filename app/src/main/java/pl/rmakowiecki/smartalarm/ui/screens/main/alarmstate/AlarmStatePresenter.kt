package pl.rmakowiecki.smartalarm.ui.screens.main.alarmstate

import pl.rmakowiecki.smartalarm.base.Contracts
import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class AlarmStatePresenter @Inject constructor() : MviPresenter<AlarmState.View, Contracts.ViewState>() {

    override fun bindIntents() = Unit
}