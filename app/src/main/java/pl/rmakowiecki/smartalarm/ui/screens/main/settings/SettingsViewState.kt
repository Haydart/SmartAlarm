package pl.rmakowiecki.smartalarm.ui.screens.main.settings

import pl.rmakowiecki.smartalarm.base.Contracts

class SettingsViewState(
        private val photosCount: Int = 10,
        private val photosSequenceInterval: Int = 500
) : Contracts.ViewState