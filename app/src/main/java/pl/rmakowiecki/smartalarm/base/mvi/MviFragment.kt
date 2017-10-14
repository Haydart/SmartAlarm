package pl.rmakowiecki.smartalarm.base.mvi

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.rmakowiecki.smartalarm.base.Contracts

abstract class MviFragment<V : Contracts.View, VS : Contracts.ViewState, out P : MviPresenter<V, VS>> :
        Fragment(), Contracts.View {

    @get:LayoutRes
    protected abstract val layout: Int

    private lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(layout, container, false)

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this as V)
    }

    override fun onDestroyView() {
        presenter.detachView(retainInstance = true)
        super.onDestroyView()
    }

    protected abstract fun createPresenter(): P
}