package pl.rmakowiecki.smartalarm.extensions

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import java.io.Serializable

var isSharedElementTransition = false
val transitionElementsList = mutableListOf<android.util.Pair<View, String>>()

inline fun <reified T : Activity> Context.startActivity(vararg params: Extra) {
    isSharedElementTransition = false
    val intent = Intent(this, T::class.java)

    params.forEach {
        when (it) {
            is Extra.SharedView -> {
                isSharedElementTransition = true
                handleSharedTransitionElement(it.view)
            }
            is Extra.PlainArgument -> {
                handlePlainExtraElement(it.resourceIdentifier, it.argument, intent)
            }
            is Extra.IntentFlags -> {
                intent.addFlags(it.flags)
            }
        }
    }

    if (isSharedElementTransition) {
        if (this !is Activity) {
            throw IllegalAccessException("Tried to perform shared element transition from outside of an Activity.")
        }
        val options = ActivityOptions.makeSceneTransitionAnimation(this, *transitionElementsList.toTypedArray())
        startActivity(intent, options.toBundle())
    } else {
        startActivity(intent)
    }
}

fun handleSharedTransitionElement(view: View) {
    if (view.transitionName !is String) {
        Log.e("startActivity ", "Warning: no transitionName for view ${view.id}, but it's been passed as shared element for transition.")
    } else {
        transitionElementsList.add(android.util.Pair(view, view.transitionName))
    }
}

fun handlePlainExtraElement(resourceIdentifier: String, value: Any, intent: Intent) {
    when (value) {
        is Int -> intent.putExtra(resourceIdentifier, value)
        is Long -> intent.putExtra(resourceIdentifier, value)
        is CharSequence -> intent.putExtra(resourceIdentifier, value)
        is String -> intent.putExtra(resourceIdentifier, value)
        is Float -> intent.putExtra(resourceIdentifier, value)
        is Double -> intent.putExtra(resourceIdentifier, value)
        is Char -> intent.putExtra(resourceIdentifier, value)
        is Short -> intent.putExtra(resourceIdentifier, value)
        is Boolean -> intent.putExtra(resourceIdentifier, value)
        is Serializable -> intent.putExtra(resourceIdentifier, value)
        is Bundle -> intent.putExtra(resourceIdentifier, value)
        is Parcelable -> intent.putExtra(resourceIdentifier, value)
        is Array<*> -> when {
            value.isArrayOf<CharSequence>() -> intent.putExtra(resourceIdentifier, value)
            value.isArrayOf<String>() -> intent.putExtra(resourceIdentifier, value)
            value.isArrayOf<Parcelable>() -> intent.putExtra(resourceIdentifier, value)
            else -> throw IllegalStateException(
                    "Intent extra $resourceIdentifier has wrong type ${value.javaClass.name}"
            )
        }
        is IntArray -> intent.putExtra(resourceIdentifier, value)
        is LongArray -> intent.putExtra(resourceIdentifier, value)
        is FloatArray -> intent.putExtra(resourceIdentifier, value)
        is DoubleArray -> intent.putExtra(resourceIdentifier, value)
        is CharArray -> intent.putExtra(resourceIdentifier, value)
        is ShortArray -> intent.putExtra(resourceIdentifier, value)
        is BooleanArray -> intent.putExtra(resourceIdentifier, value)
        else -> throw IllegalAccessException(
                "Intent extra $resourceIdentifier has wrong type ${value.javaClass.name}"
        )
    }
}

sealed class Extra {
    class IntentFlags(val flags: Int) : Extra()
    class SharedView(val view: View) : Extra()
    class PlainArgument(val resourceIdentifier: String, val argument: Any) : Extra()
}