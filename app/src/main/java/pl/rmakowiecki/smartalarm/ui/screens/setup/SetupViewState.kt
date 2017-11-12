package pl.rmakowiecki.smartalarm.ui.screens.setup

import pl.rmakowiecki.smartalarm.base.Contracts

data class SetupViewState(
        private val isInitialTextShown: Boolean = false,
        private val isLoading: Boolean = false
) : Contracts.ViewState