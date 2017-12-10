package pl.rmakowiecki.smartalarm.feature.setup

import pl.rmakowiecki.smartalarm.base.Contracts

data class SetupViewState(
        val isInitialTextShown: Boolean = false,
        val isLoading: Boolean = false
) : Contracts.ViewState