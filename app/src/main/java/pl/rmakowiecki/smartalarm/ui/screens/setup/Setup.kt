package pl.rmakowiecki.smartalarm.ui.screens.setup

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts

interface Setup {
    interface View : Contracts.View {
        val ssidInputIntent: Observable<String>
        val networkPasswordInputIntent: Observable<String>
        val networkCredentialsSubmitIntent: Observable<Unit>

        fun render(viewState: SetupViewState)
    }

    interface Interactor : Contracts.Interactor {
        fun getViewStateObservable(): Observable<SetupViewState>

        fun attachSsidInputIntent(intentObservable: Observable<String>)
        fun attachNetworkPasswordInputIntent(intentObservable: Observable<String>)
        fun attachNetworkCredentialsSubmitIntent(intentObservable: Observable<Unit>)
    }
}