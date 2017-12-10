package pl.rmakowiecki.smartalarm.feature.main.alarmincidents

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.domain.main.alarmincidents.SecurityIncidentItemViewState
import pl.rmakowiecki.smartalarm.extensions.gone
import pl.rmakowiecki.smartalarm.extensions.loadImage
import pl.rmakowiecki.smartalarm.extensions.visible

class AlarmIncidentViewHolder(
        view: View
) : RecyclerView.ViewHolder(view) {

    val previewImage = view.findViewById<ImageView>(R.id.incidentPreviewImage)
    private val progressBar = view.findViewById<ProgressBar>(R.id.incidentPhotoProgressBar)
    private val overlayIcon = view.findViewById<ImageView>(R.id.incidentPhotoOverlay)
    private val dateText = view.findViewById<TextView>(R.id.triggerDateText)
    private val hourText = view.findViewById<TextView>(R.id.triggerHourText)
    private val reasonText = view.findViewById<TextView>(R.id.triggerReasonText)
    val overflowMenuButton: ImageButton = view.findViewById(R.id.popupMenuIcon)

    fun bind(model: SecurityIncidentItemViewState) = with(model) {
        if (thumbnailUrl.isNotBlank()) {
            previewImage.loadImage(thumbnailUrl, onPhotoLoadedFunc = {
                progressBar.gone()
                overlayIcon.visible()
            })
        }

        dateText.text = date
        hourText.text = hour
        reasonText.text = launchReason
    }
}