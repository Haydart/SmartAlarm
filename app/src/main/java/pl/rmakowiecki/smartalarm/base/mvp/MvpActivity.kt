package pl.rmakowiecki.smartalarm.base.mvp

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import com.calabs.loop.mobile.android.base.mvp.MvpPresenter
import pl.rmakowiecki.smartalarm.base.Contracts

abstract class MvpActivity<V : Contracts.View, out RO : Contracts.Router, P : MvpPresenter<V, RO>> : AppCompatActivity(), Contracts.View {

    @get:LayoutRes
    protected abstract val layoutRes: Int
    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        presenter = createPresenter()
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

    override fun onDestroy() {
        presenter.onViewDestroy()
        super.onDestroy()
    }

    protected abstract fun createPresenter(): P
}