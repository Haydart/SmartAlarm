package pl.rmakowiecki.smartalarm.ui.screens.main.settings

import android.content.Context
import android.content.Intent
import pl.rmakowiecki.smartalarm.di.qualifier.ActivityContext
import pl.rmakowiecki.smartalarm.extensions.Extra
import pl.rmakowiecki.smartalarm.extensions.startActivity
import pl.rmakowiecki.smartalarm.ui.screens.splash.SplashActivity
import javax.inject.Inject

class SettingsNavigator @Inject constructor(
        @ActivityContext private val context: Context
) : Settings.Navigator {

    override fun showSplashScreen() = context.startActivity<SplashActivity>(
            Extra.IntentFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    )
}