package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import android.content.Context
import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.di.qualifier.ActivityContext
import javax.inject.Inject

class AlarmHistoryInteractor @Inject constructor(
        private val alarmHistoryService: FirebaseAlarmHistoryService,
        private val reducer: AlarmViewStateReducer,
        private val navigator: AlarmHistoryNavigator
) : AlarmHistory.Interactor {

    private var viewStateObservable = Observable.empty<AlarmViewStateChange>()

    override fun getViewStateObservable(): Observable<AlarmHistoryViewState> = viewStateObservable
            .scan(AlarmHistoryViewState(), reducer::reduce)

    override fun attachArchiveIntent(intentObservable: Observable<SecurityIncident>) {
        viewStateObservable = viewStateObservable.mergeWith(intentObservable
                .flatMapSingle(alarmHistoryService::archiveIncident)
                .flatMap { Observable.empty<AlarmViewStateChange>() } //state change will come from firebase
        )
    }

    override fun attachDeletionIntent(intentObservable: Observable<SecurityIncident>) {
        viewStateObservable = viewStateObservable.mergeWith(intentObservable
                .flatMapSingle(alarmHistoryService::deleteIncident)
                .flatMap { Observable.empty<AlarmViewStateChange>() }
        )
    }

    override fun attachDetailsIntent(intentObservable: Observable<SecurityIncident>) {
        viewStateObservable = viewStateObservable.mergeWith(intentObservable
                .doOnNext { navigator.showIncidentDetailsScreen() }
                .flatMap { Observable.empty<AlarmViewStateChange>() }
        )
    }
}

class AlarmHistoryNavigator @Inject constructor(
        @ActivityContext private val context: Context
) : AlarmHistory.Navigator {

    override fun showIncidentDetailsScreen() {
        //todo navigate to incident details screen
    }
}
