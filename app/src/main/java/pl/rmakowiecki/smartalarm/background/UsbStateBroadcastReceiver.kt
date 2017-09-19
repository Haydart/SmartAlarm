package pl.rmakowiecki.smartalarm.background

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import pl.rmakowiecki.smartalarm.extensions.logD

private const val USB_STATE_CHANGE_ACTION = "android.hardware.usb.action.USB_STATE"

class UsbStateBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == USB_STATE_CHANGE_ACTION) {
            if (intent.extras.getBoolean("connected")) {
                logD("usb device connected")
            } else {
                logD("usb device disconnected")
            }
        }
    }
}