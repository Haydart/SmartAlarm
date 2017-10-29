package pl.rmakowiecki.smartalarm.base.mvi

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.rmakowiecki.smartalarm.SmartAlarmApp
import pl.rmakowiecki.smartalarm.base.Contracts
import pl.rmakowiecki.smartalarm.di.module.ActivityModule
import pl.rmakowiecki.smartalarm.di.module.FragmentComponent

abstract class MviFragment<V : Contracts.View, VS : Contracts.ViewState, out P : MviPresenter<V, VS>> :
        Fragment(), Contracts.View {

    lateinit var fragmentComponent: FragmentComponent
        private set

    @get:LayoutRes
    protected abstract val layout: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentComponent = SmartAlarmApp.get(context)
                .applicationComponent
                .fragmentComponent(ActivityModule(activity))

        injectComponents()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(layout, container, false)

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retrievePresenter().attachView(this as V)
    }

    override fun onDestroyView() {
        retrievePresenter().detachView()
        super.onDestroyView()
    }

    protected abstract fun injectComponents()

    protected abstract fun retrievePresenter(): P
}