package pl.rmakowiecki.smartalarm.feature.auth

import android.app.Activity
import android.app.AlertDialog
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.extensions.startActivity
import pl.rmakowiecki.smartalarm.feature.main.HomeActivity
import pl.rmakowiecki.smartalarm.feature.setup.SetupActivity
import javax.inject.Inject

class AuthNavigator @Inject constructor(
        private val activity: Activity
) {

    fun showHomeScreen() {
        activity.startActivity<HomeActivity>()
        activity.finish()
    }

    fun showSetupScreen() {
        activity.startActivity<SetupActivity>()
        activity.finish()
    }

    fun showResetPasswordCompleteDialog() {
        AlertDialog.Builder(activity)
                .setMessage(R.string.reset_password_complete)
                .setPositiveButton(android.R.string.ok, null)
                .create()
                .show()
    }

    fun showFailureDialog(localizedMessage: String) {
        AlertDialog.Builder(activity)
                .setMessage(localizedMessage)
                .setPositiveButton(android.R.string.ok, null)
                .create()
                .show()
    }
}