package pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents

import io.reactivex.Observable
import javax.inject.Inject

class AlarmIncidentsInteractor @Inject constructor(
        private val alarmIncidentService: FirebaseAlarmIncidentsService,
        private val reducer: AlarmViewStateReducer,
        private val navigator: AlarmIncidentsNavigator
) : AlarmIncidents.Interactor {

    private var viewStateObservable = Observable.empty<AlarmIncidentsViewStateChange>()

    override fun getViewStateObservable(): Observable<AlarmIncidentsViewState> = viewStateObservable
            .scan(AlarmIncidentsViewState(), reducer::reduce)

    init {
        observeIncidentsChanges()
    }

    private fun observeIncidentsChanges() {
        viewStateObservable = viewStateObservable
                .mergeWith(alarmIncidentService
                        .observeIncidentsChanges()
                        .map(this::mapToLocalModel)
                        .map(AlarmIncidentsViewStateChange::ItemsChanged)
                )
    }

    private fun mapToLocalModel(remoteModelList: List<SecurityIncident>) = remoteModelList.map { remoteModel ->
        SecurityIncidentItemViewState(
                remoteModel.thumbnailUrl,
                remoteModel.reason.toString(),
                remoteModel.timestamp.toString(), //todo convert to displayable date
                remoteModel.timestamp.toString()) //todo convert to displayable hour
    }

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