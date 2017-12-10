package pl.rmakowiecki.smartalarm.feature.main.settings

import pl.rmakowiecki.smartalarm.base.Contracts

data class SettingsViewState(
        val photosCount: Int = 5,
        val photosSequenceInterval: Int = 250,
        val isLoadingPhotoCount: Boolean = false,
        val isLoadingSequenceInterval: Boolean = false
) : Contracts.ViewState