package pl.rmakowiecki.smartalarm.feature.setup

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface SetupView : Contracts.View {

    val ssidInputIntent: Observable<String>
    val networkPasswordInputIntent: Observable<String>
    val networkCredentialsSubmitIntent: Observable<Unit>

    fun render(viewState: SetupViewState)
}