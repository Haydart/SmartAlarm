package pl.rmakowiecki.smartalarm.ui.screens.auth

import android.content.Context
import pl.rmakowiecki.smartalarm.di.qualifier.ActivityContext
import pl.rmakowiecki.smartalarm.extensions.startActivity
import pl.rmakowiecki.smartalarm.ui.screens.main.HomeActivity
import javax.inject.Inject

class AuthNavigator @Inject constructor(
        @ActivityContext private val context: Context
) : Auth.Navigator {

    override fun showHomeScreen() = context.startActivity<HomeActivity>()
}