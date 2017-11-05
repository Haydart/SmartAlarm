package pl.rmakowiecki.smartalarm.ui.screens.main.settings

import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_settings.*
import pl.rmakowiecki.smartalarm.R
import pl.rmakowiecki.smartalarm.base.Contracts
import pl.rmakowiecki.smartalarm.base.mvi.MviFragment
import javax.inject.Inject

class SettingsFragment : MviFragment<Settings.View, SettingsViewState, SettingsPresenter>(),
        Settings.View {

    @Inject lateinit var presenter: SettingsPresenter

    override val layout = R.layout.fragment_settings

    override val logoutIntent
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

        photosCountSeekBar.setOnProgressChangedListener { photoCountChangePublishSubject.onNext(it) }
        photosCountSeekBar.setOnProgressChangedListener { sequenceIntervalChangePublishSubject.onNext(it) }
    }

    override fun render(viewState: Contracts.ViewState) {
        //todo implement
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}