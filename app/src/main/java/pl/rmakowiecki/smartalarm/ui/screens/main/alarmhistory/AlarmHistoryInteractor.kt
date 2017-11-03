package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.extensions.logD
import javax.inject.Inject

class AlarmHistoryInteractor @Inject constructor(
        private val alarmHistoryService: FirebaseAlarmHistoryService,
        private val reducer: AlarmViewStateReducer
) : AlarmHistory.Interactor {

    private var viewStateObservable = Observable.empty<AlarmHistoryViewState>()

    override fun getViewStateObservable(): Observable<AlarmHistoryViewState> = viewStateObservable
            .scan(AlarmHistoryViewState(), reducer::reduce)

    override fun attachArchiveIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable.mergeWith(intentObservable
                .doOnEach { logD("archiving $it") }
                .map { AlarmHistoryViewState() }
        )
    }

    override fun attachDeletionIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable.mergeWith(intentObservable
                .doOnEach { logD("deleting $it") }
                .map { AlarmHistoryViewState() }
        )
    }

    override fun attachDetailsIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable.mergeWith(intentObservable
                .doOnEach { logD("details of $it") }
                .map { AlarmHistoryViewState() }
        )
    }
}

class AlarmViewStateReducer @Inject constructor() {

    fun reduce(currentViewState: AlarmHistoryViewState, change: AlarmViewStateChange): AlarmHistoryViewState = when (change) {
        is AlarmViewStateChange.ItemArchived -> {
            currentViewState
        }
        is AlarmViewStateChange.ItemDeleted -> {
            currentViewState
        }
    }
}

sealed class AlarmViewStateChange {

    class ItemArchived(val positionInList: Int) : AlarmViewStateChange()
    class ItemDeleted(val positionInList: Int) : AlarmViewStateChange()
}

class FirebaseAlarmHistoryService : AlarmHistoryService {

    override fun archiveIncident() {
        //todo implement
    }

    override fun deleteIncident() {
        //todo implement
    }
}

interface AlarmHistoryService {
    fun archiveIncident()
    fun deleteIncident()
}
