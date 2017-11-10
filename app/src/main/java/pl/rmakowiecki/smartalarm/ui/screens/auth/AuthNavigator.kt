package pl.rmakowiecki.smartalarm.ui.screens.auth

import android.app.Activity
import android.app.AlertDialog
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.extensions.startActivity
import pl.rmakowiecki.smartalarm.ui.screens.main.HomeActivity
import javax.inject.Inject

class AuthNavigator @Inject constructor(
        private val activity: Activity
) : Auth.Navigator {

    override fun showHomeScreen() = activity.startActivity<HomeActivity>()

    override fun showResetPasswordCompleteDialog() {
        AlertDialog.Builder(activity)
                .setMessage(R.string.reset_password_complete)
                .setPositiveButton(android.R.string.ok, null)
                .create()
                .show()
    }

    override fun showFailureDialog(localizedMessage: String) {
        AlertDialog.Builder(activity)
                .setMessage(localizedMessage)
                .setPositiveButton(android.R.string.ok, null)
                .create()
                .show()
    }
}