package pl.rmakowiecki.smartalarm.base.mvp

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.calabs.loop.mobile.android.base.mvp.MvpPresenter
import pl.rmakowiecki.smartalarm.base.Contracts

abstract class MvpFragment<V : Contracts.View, out RO : Contracts.Router, P : MvpPresenter<V, RO>> : Fragment(), Contracts.View {

    @get:LayoutRes
    protected abstract val layout: Int
    protected lateinit var presenter: P

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
        presenter.onViewDestroy()
        super.onDestroyView()
    }

    protected abstract fun createPresenter(): P
}