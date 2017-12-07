package pl.rmakowiecki.smartalarm.feature.screens.main.alarmincidents

import android.app.Activity
import android.app.AlertDialog
import io.reactivex.Maybe
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.feature.screens.incidentdetails.IncidentDetailsActivity
import javax.inject.Inject

class AlarmIncidentsNavigator @Inject constructor(
        private val activity: Activity
) {

    fun showIncidentDetailsScreen() = IncidentDetailsActivity.start(activity)

    fun showDeleteConfirmationDialog(): Maybe<Boolean> = Maybe.create { emitter ->
        AlertDialog.Builder(activity)
                .setMessage(R.string.delete_confirmation_message)
                .setPositiveButton(R.string.delete, { _, _ -> emitter.onSuccess(true) })
                .setNegativeButton(R.string.cancel, { _, _ -> emitter.onComplete() })
                .create()
                .show()
    }
}