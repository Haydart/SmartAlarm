package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.extensions.loadImage

class AlarmHistoryViewHolder(
        view: View,
        private val onDetailsFuncs: (Int) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val previewImage = view.findViewById<ImageView>(R.id.historyPreviewImage)
    private val dateText = view.findViewById<TextView>(R.id.triggerDateText)
    private val reasonText = view.findViewById<TextView>(R.id.triggerReasonText)
    val overflowMenuButton: ImageButton = view.findViewById(R.id.popupMenuIcon)

    fun bind(alarmHistoryItem: AlarmHistoryItem) = with(alarmHistoryItem) {
        previewImage.loadImage(previewImageUrl)
        dateText.text = date.toString()
        reasonText.text = reason.toString()

        previewImage.setOnClickListener { onDetailsFuncs(adapterPosition) }
    }
}