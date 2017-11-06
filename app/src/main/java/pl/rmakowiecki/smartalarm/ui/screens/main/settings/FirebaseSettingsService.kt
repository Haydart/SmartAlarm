package pl.rmakowiecki.smartalarm.ui.screens.main.settings

import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseSettingsService @Inject constructor() : SettingsService {

    private val rootDatabaseNode = FirebaseDatabase
            .getInstance()
            .reference

    override fun fetchPhotoCountValue(): Single<SingleSettingResult> {
        //todo implement
        return Single.just(SingleSettingResult(true, 15))
    }

    override fun fetchPhotoSequenceIntervalValue(): Single<SingleSettingResult> {
        return Single.just(SingleSettingResult(true, 250))
    }

    override fun sendPhotoCountValue(value: Int): Single<Boolean> = Single.create { emitter ->
        rootDatabaseNode
                .child("SgVIHNDQwsPj3lmS2jS1gS9Xz5r1")
                .child("settings")
                .child("sessionPhotoCount")
                .setValue(value)
                .addOnCompleteListener { emitter.onSuccess(it.isSuccessful) }
    }

    override fun sendPhotoSequenceIntervalValue(value: Int): Single<Boolean> = Single.create { emitter ->
        rootDatabaseNode
                .child("SgVIHNDQwsPj3lmS2jS1gS9Xz5r1")
                .child("settings")
                .child("photoSequenceIntervalMillis")
                .setValue(value)
                .addOnCompleteListener { emitter.onSuccess(it.isSuccessful) }
    }
}