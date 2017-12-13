package pl.rmakowiecki.smartalarm.alarmarming

import io.reactivex.subjects.PublishSubject
import pl.rmakowiecki.smartalarm.MviViewRobot
import pl.rmakowiecki.smartalarm.feature.main.alarmarming.AlarmArmingPresenter
import pl.rmakowiecki.smartalarm.feature.main.alarmarming.AlarmArmingView
import pl.rmakowiecki.smartalarm.feature.main.alarmarming.AlarmArmingViewState

class AlarmArmingViewRobot(
        private val presenter: AlarmArmingPresenter
) : MviViewRobot<AlarmArmingViewState>(), AlarmArmingView {

    override val alarmArmingIntent: PublishSubject<Unit> = PublishSubject.create<Unit>()
    override val alarmDisarmingIntent: PublishSubject<Unit> = PublishSubject.create<Unit>()

    init {
        presenter.attachView(this)
    }

    override fun render(viewState: AlarmArmingViewState) {
        renderEvents.add(viewState)
    }

    override fun destroyView() {
        presenter.detachView()
    }
}