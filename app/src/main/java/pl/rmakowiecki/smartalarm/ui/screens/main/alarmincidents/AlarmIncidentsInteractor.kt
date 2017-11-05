package pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents

import android.content.Context
import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.di.qualifier.ActivityContext
import javax.inject.Inject

class AlarmIncidentsInteractor @Inject constructor(
        private val alarmIncidentService: FirebaseAlarmIncidentsService,
        private val reducer: AlarmViewStateReducer,
        private val navigator: AlarmIncidentsNavigator
) : AlarmIncidents.Interactor {

    private var viewStateObservable = Observable.empty<AlarmIncidentsViewStateChange>()

    override fun getViewStateObservable(): Observable<AlarmIncidentsViewState> = viewStateObservable
            .scan(AlarmIncidentsViewState(), reducer::reduce)

    override fun attachArchiveIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable.mergeWith(intentObservable
                .flatMapSingle(alarmIncidentService::archiveIncident)
                .flatMap { Observable.empty<AlarmIncidentsViewStateChange>() } //state change will come from firebase
        )
    }

    override fun attachDeletionIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable.mergeWith(intentObservable
                .flatMapSingle(alarmIncidentService::deleteIncident)
                .flatMap { Observable.empty<AlarmIncidentsViewStateChange>() }
        )
    }

    override fun attachDetailsIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable.mergeWith(intentObservable
                .doOnNext { navigator.showIncidentDetailsScreen() }
                .flatMap { Observable.empty<AlarmIncidentsViewStateChange>() }
        )
    }
}

class AlarmIncidentsNavigator @Inject constructor(
        @ActivityContext private val context: Context
) : AlarmIncidents.Navigator {

    override fun showIncidentDetailsScreen() {
        //todo navigate to incident details screen
    }
}
