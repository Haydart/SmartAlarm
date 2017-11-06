package pl.rmakowiecki.smartalarm.ui.screens.main.settings

import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseSettingsService @Inject constructor() : SettingsService {

    override fun fetchPhotoCountValue(): Single<SingleSettingResult> {
        //todo implement
        return Single.just(SingleSettingResult(true, 15))
    }

    override fun fetchPhotoSequenceIntervalValue(): Single<SingleSettingResult> {
        return Single.just(SingleSettingResult(true, 250))
    }

    override fun sendPhotoCountValue(value: Int): Single<Boolean> {
        //todo implement
        return Single.just(true).delay(1, TimeUnit.SECONDS)
    }

    override fun sendPhotoSequenceIntervalValue(value: Int): Single<Boolean> {
        //todo implement
        return Single.just(true).delay(1, TimeUnit.SECONDS)
    }
}