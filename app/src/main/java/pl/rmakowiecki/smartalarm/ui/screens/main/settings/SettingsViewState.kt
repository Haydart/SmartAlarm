package pl.rmakowiecki.smartalarm.ui.screens.main.settings

import pl.rmakowiecki.smartalarm.base.Contracts

data class SettingsViewState(
        val photosCount: Int = 10,
        val photosSequenceInterval: Int = 500,
        val isLoadingPhotoCount: Boolean = false,
        val isLoadingSequenceInterval: Boolean = false

) : Contracts.ViewState