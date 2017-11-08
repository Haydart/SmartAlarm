package pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents

import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.rmakowiecki.smartalarm.R

class AlarmIncidentsAdapter(
        var items: List<SecurityIncidentItemViewState>,
        private val onArchiveFunc: (Int) -> Unit,
        private val onDeleteFunc: (Int) -> Unit,
        private val onDetailsFuncs: (Int) -> Unit
) : RecyclerView.Adapter<AlarmIncidentViewHolder>() {

    override fun onBindViewHolder(holder: AlarmIncidentViewHolder, position: Int) = with(holder.overflowMenuButton) {
        setOnClickListener { inflateAndShowPopupMenu(it, position) }
        holder.bind(items[position])
        holder.previewImage.setOnClickListener { onDetailsFuncs(position) }
    }

    private fun inflateAndShowPopupMenu(view: View, position: Int) = PopupMenu(view.context, view).apply {
        inflate(R.menu.alarm_incident_item_popup_menu)

        setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_archive -> onArchiveFunc(position)
                R.id.action_delete -> onDeleteFunc(position)
            }
            true
        }
        show()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmIncidentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.alarm_incident_list_item, parent, false)
        return AlarmIncidentViewHolder(view)
    }
}