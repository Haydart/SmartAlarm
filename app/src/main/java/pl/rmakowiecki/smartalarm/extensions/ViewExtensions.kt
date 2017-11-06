package pl.rmakowiecki.smartalarm.extensions

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

fun EditText.setTextIfDifferent(text: String) {
    if (this.text.toString() != text) {
        this.setText(text)
    }
}

fun ImageView.loadImage(path: String, placeHolderResourceId: Int? = null, errorResourceId: Int? = null) {

    val request = Picasso
            .with(context)
            .load(path)

    if (placeHolderResourceId != null) {
        request.placeholder(placeHolderResourceId)
    }

    if (errorResourceId != null) {
        request.error(errorResourceId)
    }

    request.into(this)
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.commit()
}