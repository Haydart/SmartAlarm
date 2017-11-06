package pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.extensions.loadImage

class AlarmIncidentViewHolder(
        view: View
) : RecyclerView.ViewHolder(view) {

    private val previewImage = view.findViewById<ImageView>(R.id.incidentPreviewImage)
    private val dateText = view.findViewById<TextView>(R.id.triggerDateText)
    private val reasonText = view.findViewById<TextView>(R.id.triggerReasonText)
    val overflowMenuButton: ImageButton = view.findViewById(R.id.popupMenuIcon)

    fun bind(model: SecurityIncidentItemViewState) = with(model) {
        previewImage.loadImage(thumbnailUrl.takeIf { it.isNotBlank() } ?: "https://www.diamondbodyimage.com/wp-content/themes/fortuna/images/no_img.jpg")
        dateText.text = date
        hour
        reasonText.text = launchReason
    }
}