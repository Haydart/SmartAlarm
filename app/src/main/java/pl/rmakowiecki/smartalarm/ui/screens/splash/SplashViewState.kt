package pl.rmakowiecki.smartalarm.ui.screens.splash

import pl.rmakowiecki.smartalarm.base.Contracts

class SplashViewState(
        val afterTransition: Boolean = false
) : Contracts.ViewState {

    companion object {
        fun afterTransition() = SplashViewState(true)
    }
}