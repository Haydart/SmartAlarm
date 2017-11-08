package pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents

import android.app.Activity
import android.app.AlertDialog
import io.reactivex.Maybe
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.ui.screens.incidentdetails.IncidentDetailsActivity
import javax.inject.Inject

class AlarmIncidentsNavigator @Inject constructor(
        private val activity: Activity
) : AlarmIncidents.Navigator {

    override fun showIncidentDetailsScreen() = IncidentDetailsActivity.start(activity)

    override fun showDeleteConfirmationDialog(): Maybe<Boolean> = Maybe.create { emitter ->
        AlertDialog.Builder(activity)
                .setMessage(R.string.delete_confirmation_message)
                .setPositiveButton(R.string.delete, { _, _ -> emitter.onSuccess(true) })
                .setNegativeButton(android.R.string.cancel, { _, _ -> emitter.onComplete() })
                .create()
                .show()
    }
}