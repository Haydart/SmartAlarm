package pl.rmakowiecki.smartalarm.ui.screens.main.settings

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class SettingsPresenter @Inject constructor(
        private val interactor: SettingsInteractor
) : MviPresenter<Settings.View, SettingsViewState>() {

    override fun bindIntents() = with(interactor) {
        attachLogoutIntent(bindIntent(Settings.View::logoutIntent))

        subscribeViewState(getViewStateObservable(), Settings.View::render)
    }
}