package pl.rmakowiecki.smartalarm.feature.main.settings

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class SettingsPresenter @Inject constructor(
        private val interactor: SettingsInteractor
) : MviPresenter<SettingsView, SettingsViewState>() {

    override fun bindIntents() = with(interactor) {

        attachLogoutButtonClickIntent(
                bindIntent(SettingsView::logoutButtonClickIntent))

        attachPhotoCountInfoIntent(
                bindIntent(SettingsView::photoCountInfoIntent))

        attachSequenceIntervalInfoIntent(
                bindIntent(SettingsView::sequenceIntervalInfoIntent))

        attachPhotoCountChangeIntent(
                bindIntent(SettingsView::photoCountChangeIntent))

        attachPhotoSequenceIntervalChangeIntent(
                bindIntent(SettingsView::photoSequenceIntervalChangeIntent))


        subscribeViewState(viewStateObservable, SettingsView::render)
    }
}