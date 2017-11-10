package pl.rmakowiecki.smartalarm.ui.screens.setup

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_auth.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.ui.customView.TilingDrawable

class SetupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)
        setActivityBackground()
    }

    private fun setActivityBackground() {
        val rawDrawable = ContextCompat.getDrawable(this, R.drawable.background_vector)

        val tilingDrawable = TilingDrawable(rawDrawable)
        headerBackgroundImage.background = tilingDrawable
    }
}
