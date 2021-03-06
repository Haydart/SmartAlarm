package pl.rmakowiecki.smartalarm.feature.setup

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_setup.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.mvi.MviActivity
import pl.rmakowiecki.smartalarm.customView.TilingDrawable

class SetupActivity : MviActivity<SetupView, SetupViewState, SetupPresenter>(), SetupView {

    override val layoutRes = R.layout.activity_setup

    override val ssidInputIntent: Observable<String>
        get() = Observable.empty()

    override val networkPasswordInputIntent: Observable<String>
        get() = Observable.empty()

    override val networkCredentialsSubmitIntent: Observable<Unit>
        get() = Observable.empty()

    override fun injectComponents() = activityComponent.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityBackground()
        dottedLine.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    private fun setActivityBackground() {
        val rawDrawable = ContextCompat.getDrawable(this, R.drawable.background_pattern)

        val tilingDrawable = TilingDrawable(rawDrawable)
        headerBackgroundImage.background = tilingDrawable
    }

    override fun render(viewState: SetupViewState) {
        //todo implement
    }
}