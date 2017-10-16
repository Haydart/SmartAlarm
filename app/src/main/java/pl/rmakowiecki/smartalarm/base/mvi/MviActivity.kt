package pl.rmakowiecki.smartalarm.base.mvi

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import pl.rmakowiecki.smartalarm.base.Contracts

abstract class MviActivity<V : Contracts.View, VS : Contracts.ViewState, out P : MviPresenter<V, VS>> :
        AppCompatActivity(), Contracts.View {

    @get:LayoutRes
    protected abstract val layoutRes: Int

    abstract val shouldMoveToBack: Boolean

    private lateinit var presenter: P

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

    override fun onBackPressed() {
        if (shouldMoveToBack) {
            super.onBackPressed()
        } else {
            moveTaskToBack(true)
        }
    }

    override fun onStop() {
        presenter.detachView(retainInstance = true)
        super.onStop()
    }

    protected abstract fun createPresenter(): P
}