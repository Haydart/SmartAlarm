package pl.rmakowiecki.smartalarm.feature.coresetup

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_core_setup.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.extensions.enable

class CoreSetupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_core_setup)
        networkConnectButton.enable()
    }
}
