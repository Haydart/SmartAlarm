package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import pl.rmakowiecki.smartalarm.base.Contracts
import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class AlarmHistoryPresenter @Inject constructor() : MviPresenter<AlarmHistory.View, Contracts.ViewState>() {

    override fun bindIntents() = Unit
}