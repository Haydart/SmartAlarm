package pl.rmakowiecki.smartalarm.ui.screens.main.settings

import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding2.view.clicks
import com.sdsmdg.harjot.crollerTest.Croller
import com.sdsmdg.harjot.crollerTest.OnCrollerChangeListener
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_settings.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.mvi.MviFragment
import pl.rmakowiecki.smartalarm.extensions.invisible
import pl.rmakowiecki.smartalarm.extensions.visible
import javax.inject.Inject

class SettingsFragment : MviFragment<Settings.View, SettingsViewState, SettingsPresenter>(),
        Settings.View {

    @Inject lateinit var presenter: SettingsPresenter

    override val layout = R.layout.fragment_settings

    override val logoutButtonClickIntent
        get() = logoutButton.clicks()

    override val photoCountInfoIntent: Observable<Unit>
        get() = photoCountKnobInfoText.clicks()

    override val sequenceIntervalInfoIntent: Observable<Unit>
        get() = intervalKnobInfoText.clicks()

    private val photoCountChangePublishSubject = PublishSubject.create<Int>()

    override val photoCountChangeIntent: Observable<Int>
        get() = photoCountChangePublishSubject

    private val sequenceIntervalChangePublishSubject = PublishSubject.create<Int>()

    override val photoSequenceIntervalChangeIntent: Observable<Int>
        get() = sequenceIntervalChangePublishSubject

    override fun injectComponents() = fragmentComponent.inject(this)

    override fun retrievePresenter() = presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        logoutButton.isEnabled = true

        photosCountKnobWidget.setOnCrollerChangeListener(object : OnCrollerChangeListener {
            override fun onProgressChanged(croller: Croller, progress: Int) {
                croller.label = (croller.progress + PHOTO_COUNT_OFFSET).toString()
            }

            override fun onStartTrackingTouch(croller: Croller) = Unit

            override fun onStopTrackingTouch(croller: Croller) = photoCountChangePublishSubject.onNext(croller.progress)
        })

        photosSequenceIntervalKnobWidget.setOnCrollerChangeListener(object : OnCrollerChangeListener {
            override fun onProgressChanged(croller: Croller, progress: Int) {
                croller.label = (croller.progress * SEQUENCE_INTERVAL_MULTIPLIER + SEQUENCE_INTERVAL_OFFSET).toString()
            }

            override fun onStartTrackingTouch(croller: Croller) = Unit

            override fun onStopTrackingTouch(croller: Croller) = sequenceIntervalChangePublishSubject.onNext(croller.progress)
        })
    }

    override fun render(viewState: SettingsViewState) = with(viewState) {

        if (isLoadingPhotoCount) photoCountProgressView.visible() else photoCountProgressView.invisible()

        if (isLoadingSequenceInterval) sequenceIntervalProgressView.visible() else sequenceIntervalProgressView.invisible()

        photosCountKnobWidget.label = photosCount.toString()
        photosSequenceIntervalKnobWidget.label = photosSequenceInterval.toString()
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}