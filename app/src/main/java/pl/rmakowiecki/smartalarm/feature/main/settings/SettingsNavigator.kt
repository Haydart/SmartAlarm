package pl.rmakowiecki.smartalarm.feature.main.settings

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import io.reactivex.Maybe
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.extensions.Extra
import pl.rmakowiecki.smartalarm.extensions.startActivity
import pl.rmakowiecki.smartalarm.feature.splash.SplashActivity
import javax.inject.Inject

class SettingsNavigator @Inject constructor(
        private val activity: Activity
) {

    fun showPhotoCountInfoDialog() = AlertDialog.Builder(activity)
            .setMessage(R.string.photo_count_description)
            .setPositiveButton(android.R.string.ok, null)
            .create()
            .show()

    fun showSequenceIntervalInfoDialog() = AlertDialog.Builder(activity)
            .setMessage(R.string.sequence_interval_description)
            .setPositiveButton(android.R.string.ok, null)
            .create()
            .show()

    fun showLogoutConfirmationDialog(): Maybe<Boolean> = Maybe.create { emitter ->
        AlertDialog.Builder(activity)
                .setMessage(R.string.logout_confirmation_message)
                .setPositiveButton(R.string.logout, { _, _ -> emitter.onSuccess(true) })
                .setNegativeButton(R.string.cancel, { _, _ -> emitter.onComplete() })
                .create()
                .show()
    }

    fun showSplashScreen() = activity.startActivity<SplashActivity>(
            Extra.IntentFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    )
}