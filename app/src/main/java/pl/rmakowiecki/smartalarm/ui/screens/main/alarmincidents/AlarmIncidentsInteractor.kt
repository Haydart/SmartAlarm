package pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents.AlarmIncidentsViewStateChange.*
import pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents.IncidentOperation.Removed
import pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents.IncidentOperation.Updated
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
        checkIncidentsAvailability()
        observeIncidentsChanges()
    }

    private fun checkIncidentsAvailability() {
        viewStateObservable = viewStateObservable
                .mergeWith(alarmIncidentService
                        .isIncidentsListEmpty()
                        .toObservable()
                        .map(AlarmIncidentsViewStateChange::ItemsEmpty)
                )
    }

    private fun observeIncidentsChanges() {
        viewStateObservable = viewStateObservable
                .mergeWith(alarmIncidentService
                        .observeIncidentsChanges()
                        .map(this::mapToViewStateChange)

                )
    }

    private fun mapToViewStateChange(incidentChange: IncidentChange): AlarmIncidentsViewStateChange = with(incidentChange) {
        when (incidentChange.operation) {
            is IncidentOperation.Added -> {
                ItemAdded(
                        mapToLocalModel(incidentChange.model)
                )
            }
            is Removed -> {
                ItemRemoved(
                        mapToLocalModel(incidentChange.model),
                        incidentChange.operation.removedIndex
                )
            }
            is Updated -> {
                ItemUpdated(
                        mapToLocalModel(incidentChange.model),
                        incidentChange.operation.changedIndex
                )
            }
        }
    }

    private fun mapToLocalModel(remoteModel: SecurityIncident) = with(remoteModel) {
        SecurityIncidentItemViewState(
                thumbnailUrl,
                reason.toString(),
                timestamp.toString(), //todo convert to displayable date
                timestamp.toString() //todo convert to displayable hour
        )
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