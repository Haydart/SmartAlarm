package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.extensions.logD

class AlarmHistoryAdapter(
        private val items: MutableList<AlarmHistoryItem>
) : RecyclerView.Adapter<AlarmHistoryViewHolder>() {

    override fun onBindViewHolder(holder: AlarmHistoryViewHolder, position: Int) = with(holder.overflowMenuButton) {
        setOnClickListener { inflateAndShowPopupMenu(it) }
        holder.bind(items[position])
    }

    private fun inflateAndShowPopupMenu(view: View) {
        PopupMenu(view.context, view).apply {
            inflate(R.menu.alarm_history_item_popup_menu)

            setOnMenuItemClickListener { menuItem ->
                val option = menuItem.title.toString()
                logD(option)
                true
            }
            show()
        }
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmHistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_list_item, parent, false)
        return AlarmHistoryViewHolder(view)
    }
}