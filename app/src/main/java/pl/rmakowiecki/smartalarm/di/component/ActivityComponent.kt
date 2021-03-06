package pl.rmakowiecki.smartalarm.di.component

import dagger.Subcomponent
import pl.rmakowiecki.smartalarm.di.ActivityScope
import pl.rmakowiecki.smartalarm.di.module.ActivityModule
import pl.rmakowiecki.smartalarm.feature.auth.AuthActivity
import pl.rmakowiecki.smartalarm.feature.incidentdetails.IncidentDetailsActivity
import pl.rmakowiecki.smartalarm.feature.main.HomeActivity
import pl.rmakowiecki.smartalarm.feature.setup.SetupActivity
import pl.rmakowiecki.smartalarm.feature.splash.SplashActivity

@ActivityScope
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(splashActivity: SplashActivity)
    fun inject(authActivity: AuthActivity)
    fun inject(homeActivity: HomeActivity)
    fun inject(incidentDetailsActivity: IncidentDetailsActivity)
    fun inject(setupActivity: SetupActivity)
}