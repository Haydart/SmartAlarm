package pl.rmakowiecki.smartalarm.ui.screens.main.settings

import io.reactivex.Single

interface SettingsService {

    fun sendPhotoCountValue(value: Int): Single<Boolean>
    fun sendPhotoSequenceIntervalValue(value: Int): Single<Boolean>
    fun fetchPhotoCountValue(): Single<SingleSettingResult>
    fun fetchPhotoSequenceIntervalValue(): Single<SingleSettingResult>
}