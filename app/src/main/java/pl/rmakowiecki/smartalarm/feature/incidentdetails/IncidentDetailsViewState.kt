package pl.rmakowiecki.smartalarm.feature.incidentdetails

import pl.rmakowiecki.smartalarm.base.Contracts

data class IncidentDetailsViewState(
        val currentPhotoPage: Int = 0,
        val photoUrlsList: List<String> = emptyList()
) : Contracts.ViewState