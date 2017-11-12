package pl.rmakowiecki.smartalarm.ui.screens.setup

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_setup.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.mvi.MviActivity
import pl.rmakowiecki.smartalarm.ui.customView.TilingDrawable
import javax.inject.Inject

class SetupActivity : MviActivity<Setup.View, SetupViewState, SetupPresenter>(), Setup.View {

    @Inject lateinit var presenter: SetupPresenter

    override val layoutRes = R.layout.activity_setup

    override val ssidInputIntent: Observable<String>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override val networkPasswordInputIntent: Observable<String>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override val networkCredentialsSubmitIntent: Observable<Unit>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun retrievePresenter() = presenter

    override fun injectComponents() = activityComponent.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityBackground()
        dottedLine.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    private fun setActivityBackground() {
        val rawDrawable = ContextCompat.getDrawable(this, R.drawable.overlay_pattern_background_vector)

        val tilingDrawable = TilingDrawable(rawDrawable)
        headerBackgroundImage.background = tilingDrawable
    }

    override fun render(viewState: SetupViewState) {
        //todo implement
    }
}