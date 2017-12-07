package pl.rmakowiecki.smartalarm.feature.screens.main.settings

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class SettingsPresenter @Inject constructor(
        private val interactor: SettingsInteractor
) : MviPresenter<SettingsView, SettingsViewState>() {

    override fun bindIntents() = with(interactor) {

        attachLogoutButtonClickIntent(
                intent(SettingsView::logoutButtonClickIntent))

        attachPhotoCountInfoIntent(
                intent(SettingsView::photoCountInfoIntent))

        attachSequenceIntervalInfoIntent(
                intent(SettingsView::sequenceIntervalInfoIntent))

        attachPhotoCountChangeIntent(
                intent(SettingsView::photoCountChangeIntent))

        attachPhotoSequenceIntervalChangeIntent(
                intent(SettingsView::photoSequenceIntervalChangeIntent))


        subscribeViewState(viewStateObservable, SettingsView::render)
    }
}