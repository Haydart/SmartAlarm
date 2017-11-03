package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.fragment_alarm_history.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.Contracts
import pl.rmakowiecki.smartalarm.base.mvi.MviFragment
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
        /*adapter = AlarmHistoryAdapter(mutableListOf(
                AlarmHistoryItem("http://koncha.890m.com/wp-content/uploads/2016/06/2.jpg", 100, BEAM_BREAK_DETECTOR),
                AlarmHistoryItem("https://googlechrome.github.io/samples/picture-element/images/butterfly.jpg", 200, MOTION_SENSOR),
                AlarmHistoryItem("http://koncha.890m.com/wp-content/uploads/2016/06/2.jpg", 300, BEAM_BREAK_DETECTOR),
                AlarmHistoryItem("http://koncha.890m.com/wp-content/uploads/2016/06/2.jpg", 400, BEAM_BREAK_DETECTOR),
                AlarmHistoryItem("http://koncha.890m.com/wp-content/uploads/2016/06/2.jpg", 500, MOTION_SENSOR)
        ))*/
        registerForContextMenu(this)
    }

    companion object {
        fun newInstance() = AlarmHistoryFragment()
    }
}