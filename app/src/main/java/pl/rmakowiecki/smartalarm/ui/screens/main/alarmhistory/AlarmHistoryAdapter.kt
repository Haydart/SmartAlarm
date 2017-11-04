package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.rmakowiecki.smartalarm.R

class AlarmHistoryAdapter(
        private val items: MutableList<SecurityIncident>,
        private val onArchiveFunc: (SecurityIncident) -> Unit,
        private val onDeleteFunc: (SecurityIncident) -> Unit,
        private val onDetailsFuncs: (SecurityIncident) -> Unit
) : RecyclerView.Adapter<AlarmHistoryViewHolder>() {

    override fun onBindViewHolder(holder: AlarmHistoryViewHolder, position: Int) = with(holder.overflowMenuButton) {
        setOnClickListener { inflateAndShowPopupMenu(it, position) }
        holder.bind(items[position])
        holder.itemView.setOnClickListener { onDetailsFuncs(items[position]) }
    }

    private fun inflateAndShowPopupMenu(view: View, position: Int) = PopupMenu(view.context, view).apply {
        inflate(R.menu.alarm_history_item_popup_menu)

        setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_archive -> onArchiveFunc(items[position])
                R.id.action_delete -> onDeleteFunc(items[position])
            }
            true
        }
        show()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmHistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_list_item, parent, false)
        return AlarmHistoryViewHolder(view)
    }
}