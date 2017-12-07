package pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents

import io.reactivex.Observable
import pl.rmakowiecki.smartalarm.data.main.alarmincidents.DetailsGateway
import pl.rmakowiecki.smartalarm.data.main.alarmincidents.FirebaseAlarmIncidentsService
import pl.rmakowiecki.smartalarm.data.main.alarmincidents.IncidentChange
import pl.rmakowiecki.smartalarm.data.main.alarmincidents.IncidentOperation
import pl.rmakowiecki.smartalarm.data.main.alarmincidents.IncidentOperation.Removed
import pl.rmakowiecki.smartalarm.data.main.alarmincidents.IncidentOperation.Updated
import pl.rmakowiecki.smartalarm.domain.main.alarmincidents.AlarmIncidentModelMapper
import pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents.AlarmIncidentsViewStateChange.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val SNACKBAR_HIDE_DELAY = 2L

class AlarmIncidentsInteractor @Inject constructor(
        private val alarmIncidentService: FirebaseAlarmIncidentsService,
        private val reducer: AlarmIncidentsViewStateReducer,
        private val navigator: AlarmIncidentsNavigator,
        private val modelMapper: AlarmIncidentModelMapper,
        private val detailsLogicGateway: DetailsGateway
) {

    private var viewStateObservable = Observable.empty<AlarmIncidentsViewStateChange>()

    fun getViewStateObservable(): Observable<AlarmIncidentsViewState> = viewStateObservable
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
                        modelMapper.mapToLocalModel(incidentChange.model)
                )
            }
            is Removed -> {
                ItemRemoved(
                        modelMapper.mapToLocalModel(incidentChange.model),
                        incidentChange.operation.removedIndex
                )
            }
            is Updated -> {
                ItemUpdated(
                        modelMapper.mapToLocalModel(incidentChange.model),
                        incidentChange.operation.changedIndex
                )
            }
        }
    }

    fun attachArchiveIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable
                .mergeWith(intentObservable
                        .flatMapSingle(alarmIncidentService::archiveIncident)
                        .flatMap { Observable.empty<AlarmIncidentsViewStateChange>() }) //state change will come from firebase
                .mergeWith(intentObservable
                        .map { SnackBarShown("Incident archived") }) //todo get other language resources
                .mergeWith(intentObservable
                        .switchMap {
                            Observable.just(SnackBarHidden())
                                    .delay(SNACKBAR_HIDE_DELAY, TimeUnit.SECONDS)
                        })
    }

    fun attachDeletionIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable
                .mergeWith(intentObservable
                        .flatMapMaybe { listPosition ->
                            navigator.showDeleteConfirmationDialog()
                                    .doOnSuccess { alarmIncidentService.deleteIncident(listPosition) }
                        }.flatMap { Observable.empty<AlarmIncidentsViewStateChange>() })
                .mergeWith(intentObservable
                        .map { SnackBarShown("Incident deleted") }) //todo get other language resources
                .mergeWith(intentObservable
                        .switchMap {
                            Observable.just(SnackBarHidden())
                                    .delay(SNACKBAR_HIDE_DELAY, TimeUnit.SECONDS)
                        })

    }

    fun attachSnackBarDismissIntent(intentObservable: Observable<Unit>) {
        viewStateObservable = viewStateObservable
                .mergeWith(intentObservable
                        .map { SnackBarHidden() })
    }

    fun attachDetailsIntent(intentObservable: Observable<Int>) {
        viewStateObservable = viewStateObservable.mergeWith(intentObservable
                .doOnNext {
                    detailsLogicGateway.incidentBackendIdSingle = alarmIncidentService.fetchIdForListPosition(it)
                    navigator.showIncidentDetailsScreen()
                }
                .flatMap { Observable.empty<AlarmIncidentsViewStateChange>() }
        )
    }
}