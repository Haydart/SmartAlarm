package pl.rmakowiecki.smartalarm.ui.screens.main.settings

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class SettingsPresenter @Inject constructor(
        private val interactor: SettingsInteractor
) : MviPresenter<Settings.View, SettingsViewState>() {

    override fun bindIntents() = with(interactor) {

        attachLogoutButtonClickIntent(
                bindIntent(Settings.View::logoutButtonClickIntent)
        )
        attachPhotoCountInfoIntent(
                bindIntent(Settings.View::photoCountInfoIntent)
        )
        attachSequenceIntervalInfoIntent(
                bindIntent(Settings.View::sequenceIntervalInfoIntent)
        )
        attachPhotoCountChangeIntent(
                bindIntent(Settings.View::photoCountChangeIntent)
        )
        attachPhotoSequenceIntervalChangeIntent(
                bindIntent(Settings.View::photoSequenceIntervalChangeIntent)
        )

        subscribeViewState(getViewStateObservable(), Settings.View::render)
    }
}