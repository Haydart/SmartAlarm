package pl.rmakowiecki.smartalarm.ui.screens.main.alarmincidents

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_alarm_incidents.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.mvi.MviFragment
import pl.rmakowiecki.smartalarm.extensions.gone
import pl.rmakowiecki.smartalarm.extensions.visible
import javax.inject.Inject

class AlarmIncidentsFragment : MviFragment<AlarmIncidents.View, AlarmIncidentsViewState, AlarmIncidentsPresenter>(),
        AlarmIncidents.View {

    @Inject lateinit var presenter: AlarmIncidentsPresenter

    override val layout = R.layout.fragment_alarm_incidents

    private val archiveIntentPublishSubject = PublishSubject.create<Int>()

    override val incidentArchivingIntent: Observable<Int>
        get() = archiveIntentPublishSubject

    private val deleteIntentPublishSubject = PublishSubject.create<Int>()

    override val incidentDeletionIntent: Observable<Int>
        get() = deleteIntentPublishSubject

    private val incidentsDetailsIntentPublishSubject = PublishSubject.create<Int>()

    override val incidentDetailsIntent: Observable<Int>
        get() = incidentsDetailsIntentPublishSubject

    override fun injectComponents() = fragmentComponent.inject(this)

    override fun retrievePresenter() = presenter

    private val incidentsAdapter = AlarmIncidentsAdapter(
            mutableListOf(),
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

    override fun render(viewState: AlarmIncidentsViewState) = with(viewState) {
        incidentsAdapter.items = viewState.incidentsList
        incidentsAdapter.notifyDataSetChanged()

        if (isPlaceholderVisible) listPlaceholder.visible() else listPlaceholder.gone()

        if (incidentsList.isEmpty()) recyclerView.gone() else recyclerView.visible()

        if (isLoading) progressBar.visible() else progressBar.gone()
    }

    companion object {
        fun newInstance() = AlarmIncidentsFragment()
    }
}