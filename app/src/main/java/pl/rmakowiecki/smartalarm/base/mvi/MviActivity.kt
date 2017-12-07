package pl.rmakowiecki.smartalarm.base.mvi

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import pl.rmakowiecki.smartalarm.SmartAlarmApp
import pl.rmakowiecki.smartalarm.base.Contracts
import pl.rmakowiecki.smartalarm.di.component.ActivityComponent
import pl.rmakowiecki.smartalarm.di.module.ActivityModule
import javax.inject.Inject

abstract class MviActivity<V : Contracts.View, VS : Contracts.ViewState, P : MviPresenter<V, VS>> :
        AppCompatActivity(), Contracts.View {

    @get:LayoutRes
    protected abstract val layoutRes: Int

    @Inject lateinit var presenter: P

    lateinit var activityComponent: ActivityComponent
        private set

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        activityComponent = SmartAlarmApp.get(this)
                .applicationComponent
                .activityComponent(ActivityModule(this))

        injectComponents()
    }

    @Suppress("UNCHECKED_CAST")
    override fun onStart() {
        super.onStart()
        presenter.attachView(this as V)
    }

    override fun onStop() {
        presenter.detachView()
        super.onStop()
    }

    protected abstract fun injectComponents()
}