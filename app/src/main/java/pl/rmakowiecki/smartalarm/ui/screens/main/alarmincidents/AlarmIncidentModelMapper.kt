package pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents

import android.content.Context
import android.os.Build
import pl.rmakowiecki.smartalarm.di.qualifier.ActivityContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AlarmIncidentModelMapper @Inject constructor(
        @ActivityContext val context: Context) {

    fun mapToLocalModel(remoteModel: SecurityIncident) = with(remoteModel) {
        SecurityIncidentItemViewState(
                thumbnailUrl,
                reason.toString(),
                SimpleDateFormat("dd-MM-yyyy", getCurrentLocale(context)).format(timestamp),
                SimpleDateFormat("HH:mm:ss", getCurrentLocale(context)).format(timestamp)
        )
    }

    private fun getCurrentLocale(context: Context): Locale =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                context.resources.configuration.locales.get(0)
            else context.resources.configuration.locale
}