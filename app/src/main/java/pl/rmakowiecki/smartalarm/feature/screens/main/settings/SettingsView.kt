package pl.rmakowiecki.smartalarm.feature.screens.main.settings

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface SettingsView : Contracts.View {

    val logoutButtonClickIntent: Observable<Unit>
    val photoCountChangeIntent: Observable<Int>
    val photoSequenceIntervalChangeIntent: Observable<Int>
    val photoCountInfoIntent: Observable<Unit>
    val sequenceIntervalInfoIntent: Observable<Unit>

    fun render(viewState: SettingsViewState)
}