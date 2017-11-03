package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_alarm_history.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.mvi.MviFragment
import javax.inject.Inject

class AlarmHistoryFragment : MviFragment<AlarmHistory.View, AlarmHistoryViewState, AlarmHistoryPresenter>(),
        AlarmHistory.View {

    @Inject lateinit var presenter: AlarmHistoryPresenter

    private val archiveIntentPublishSubject = PublishSubject.create<Int>()

    override val incidentArchivingIntent: Observable<Int>
        get() = archiveIntentPublishSubject

    private val deleteIntentPublishSubject = PublishSubject.create<Int>()

    override val incidentDeletionIntent: Observable<Int>
        get() = deleteIntentPublishSubject

    override val layout = R.layout.fragment_alarm_history

    override fun injectComponents() = fragmentComponent.inject(this)

    override fun retrievePresenter() = presenter

    private val incidentsAdapter = AlarmHistoryAdapter(mutableListOf())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() = with(recyclerView) {
        layoutManager = LinearLayoutManager(context)
        adapter = incidentsAdapter
        registerForContextMenu(this)
    }

    override fun render(viewState: AlarmHistoryViewState) {
        //todo implement
    }

    companion object {
        fun newInstance() = AlarmHistoryFragment()
    }
}