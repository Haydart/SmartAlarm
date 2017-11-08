package pl.rmakowiecki.smartalarm.ui.screens.incidentdetails

import pl.rmakowiecki.smartalarm.base.Contracts

data class IncidentDetailsViewState(
        val currentPhotoPage: Int = 0,
        val photoUrlsList: List<String> = emptyList()
) : Contracts.ViewState