package pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_alarm_history.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.mvi.MviFragment
import pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory.AlarmTriggerReason.BEAM_BREAK_DETECTOR
import pl.rmakowiecki.smartalarm.ui.screens.main.alarmhistory.AlarmTriggerReason.MOTION_SENSOR
import javax.inject.Inject

class AlarmHistoryFragment : MviFragment<AlarmHistory.View, AlarmHistoryViewState, AlarmHistoryPresenter>(),
        AlarmHistory.View {

    @Inject lateinit var presenter: AlarmHistoryPresenter

    override val layout = R.layout.fragment_alarm_history

    private val archiveIntentPublishSubject = PublishSubject.create<SecurityIncident>()

    override val incidentArchivingIntent: Observable<SecurityIncident>
        get() = archiveIntentPublishSubject

    private val deleteIntentPublishSubject = PublishSubject.create<SecurityIncident>()

    override val incidentDeletionIntent: Observable<SecurityIncident>
        get() = deleteIntentPublishSubject

    private val incidentsDetailsIntentPublishSubject = PublishSubject.create<SecurityIncident>()

    override val incidentDetailsIntent: Observable<SecurityIncident>
        get() = incidentsDetailsIntentPublishSubject

    override fun injectComponents() = fragmentComponent.inject(this)

    override fun retrievePresenter() = presenter

    private val incidentsAdapter = AlarmHistoryAdapter(
            mutableListOf(
                    SecurityIncident("http://koncha.890m.com/wp-content/uploads/2016/06/2.jpg", 100, BEAM_BREAK_DETECTOR),
                    SecurityIncident("https://googlechrome.github.io/samples/picture-element/images/butterfly.jpg", 200, MOTION_SENSOR),
                    SecurityIncident("http://koncha.890m.com/wp-content/uploads/2016/06/2.jpg", 300, BEAM_BREAK_DETECTOR),
                    SecurityIncident("http://koncha.890m.com/wp-content/uploads/2016/06/2.jpg", 400, BEAM_BREAK_DETECTOR),
                    SecurityIncident("http://koncha.890m.com/wp-content/uploads/2016/06/2.jpg", 500, MOTION_SENSOR)
            ),
            { model -> archiveIntentPublishSubject.onNext(model) },
            { model -> deleteIntentPublishSubject.onNext(model) },
            { model -> incidentsDetailsIntentPublishSubject.onNext(model) }
    )

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