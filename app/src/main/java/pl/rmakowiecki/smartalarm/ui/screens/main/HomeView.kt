package pl.rmakowiecki.smartalarm.ui.screens.main

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface HomeView : Contracts.View {

    val tabSwitchIntent: Observable<Int>

    fun render(viewState: HomeViewState)
}