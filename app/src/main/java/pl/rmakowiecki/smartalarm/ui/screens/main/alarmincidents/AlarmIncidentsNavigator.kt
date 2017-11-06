package pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents

import android.content.Context
import pl.rmakowiecki.smartalarm.di.qualifier.ActivityContext
import pl.rmakowiecki.smartalarm.ui.screens.incidentdetails.IncidentDetailsActivity
import javax.inject.Inject

class AlarmIncidentsNavigator @Inject constructor(
        @ActivityContext private val context: Context
) : AlarmIncidents.Navigator {

    override fun showIncidentDetailsScreen() = IncidentDetailsActivity.start(context)
}