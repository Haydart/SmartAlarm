package pl.rmakowiecki.smartalarm.ui.screens.main.settings

import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseSettingsService @Inject constructor() : SettingsService {

    override fun fetchPhotoCountValue(): Single<SingleSettingResult> {
        //todo implement
    }

    override fun fetchPhotoSequenceIntervalValue(): Single<SingleSettingResult> {
        //todo implement
    }

    override fun sendPhotoCountValue(value: Int): Single<Boolean> {
        //todo implement
    }

    override fun sendPhotoSequenceIntervalValue(value: Int): Single<Boolean> {
        //todo implement
    }
}