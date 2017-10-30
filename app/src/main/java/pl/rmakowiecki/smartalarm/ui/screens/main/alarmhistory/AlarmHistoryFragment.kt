package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_alarm_history.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.Contracts
import pl.rmakowiecki.smartalarm.base.mvi.MviFragment
import pl.rmakowiecki.smartalarm.extensions.loadImage
import pl.rmakowiecki.smartalarm.extensions.logD
import pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory.AlarmTriggerReason.BEAM_BREAK_DETECTOR
import pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory.AlarmTriggerReason.MOTION_SENSOR
import javax.inject.Inject

class AlarmHistoryFragment : MviFragment<AlarmHistory.View, Contracts.ViewState, AlarmHistoryPresenter>() {

    @Inject lateinit var presenter: AlarmHistoryPresenter

    override val layout = R.layout.fragment_alarm_history

    override fun injectComponents() = fragmentComponent.inject(this)

    override fun retrievePresenter() = presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() = with(recyclerView) {
        layoutManager = LinearLayoutManager(context)
        adapter = AlarmHistoryAdapter(mutableListOf(
                AlarmHistoryItem("http://koncha.890m.com/wp-content/uploads/2016/06/2.jpg", 100, BEAM_BREAK_DETECTOR),
                AlarmHistoryItem("https://googlechrome.github.io/samples/picture-element/images/butterfly.jpg", 200, MOTION_SENSOR),
                AlarmHistoryItem("http://koncha.890m.com/wp-content/uploads/2016/06/2.jpg", 300, BEAM_BREAK_DETECTOR),
                AlarmHistoryItem("http://koncha.890m.com/wp-content/uploads/2016/06/2.jpg", 400, BEAM_BREAK_DETECTOR),
                AlarmHistoryItem("http://koncha.890m.com/wp-content/uploads/2016/06/2.jpg", 500, MOTION_SENSOR)
        ))
        registerForContextMenu(this)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var position = (recyclerView.adapter as AlarmHistoryAdapter).menuPosition

        when (item.itemId) {
            R.id.action_archive -> {
                logD("archive $position")
            }
            R.id.action_delete -> {
            }
        }

        return super.onContextItemSelected(item)
    }

    companion object {
        fun newInstance() = AlarmHistoryFragment()
    }
}

class AlarmHistoryAdapter(
        private val items: MutableList<AlarmHistoryItem>
) : RecyclerView.Adapter<AlarmHistoryViewHolder>() {

    var menuPosition = 0
        private set

    override fun onBindViewHolder(holder: AlarmHistoryViewHolder, position: Int) {
        holder.itemView.setOnLongClickListener {
            menuPosition = holder.adapterPosition
            false
        }
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmHistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_list_item, parent, false)
        return AlarmHistoryViewHolder(view)
    }

    override fun onViewRecycled(holder: AlarmHistoryViewHolder) {
        holder.itemView.setOnLongClickListener(null)
        super.onViewRecycled(holder)
    }

}

class AlarmHistoryItem(
        val previewImageUrl: String,
        val date: Long,
        val reason: AlarmTriggerReason
)

enum class AlarmTriggerReason {
    BEAM_BREAK_DETECTOR,
    MOTION_SENSOR
}

class AlarmHistoryViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {

    private val previewImage = view.findViewById<ImageView>(R.id.historyPreviewImage)
    private val dateText = view.findViewById<TextView>(R.id.triggerDateText)
    private val reasonText = view.findViewById<TextView>(R.id.triggerReasonText)

    init {
        view.setOnCreateContextMenuListener(this)
    }

    fun bind(alarmHistoryItem: AlarmHistoryItem) = with(alarmHistoryItem) {
        previewImage.loadImage(previewImageUrl)
        dateText.text = date.toString()
        reasonText.text = reason.toString()
    }

    override fun onCreateContextMenu(menu: ContextMenu, view: View, menuInfo: ContextMenu.ContextMenuInfo) {
        menu.add(Menu.NONE, R.id.action_archive, Menu.NONE, R.string.action_archive)
        menu.add(Menu.NONE, R.id.action_delete, Menu.NONE, R.string.action_delete)
    }
}

