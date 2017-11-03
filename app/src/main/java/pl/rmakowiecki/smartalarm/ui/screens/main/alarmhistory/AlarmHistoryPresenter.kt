package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.base.Contracts
import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class AlarmHistoryPresenter @Inject constructor(
        private val interactor: AlarmHistoryInteractor
) : MviPresenter<AlarmHistory.View, Contracts.ViewState>() {

    override fun bindIntents() = with(interactor) {

    }
}

class AlarmHistoryInteractor : AlarmHistory.Interactor {

    private var viewStateObservable = Observable.empty<AlarmHistoryViewState>()

    override fun getViewStateObservable(): Observable<AlarmHistoryViewState> = viewStateObservable

    override fun attachArchiveIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable.mergeWith(
                intentObservable.map { AlarmHistoryViewState() }
        )
    }

    override fun attachDeletionIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable.mergeWith(
                intentObservable.map { AlarmHistoryViewState() }
        )
    }

}
