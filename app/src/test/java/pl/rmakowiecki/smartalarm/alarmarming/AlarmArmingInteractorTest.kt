package pl.rmakowiecki.smartalarm.alarmarming

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import pl.rmakowiecki.smartalarm.RxSchedulersOverrideRule
import pl.rmakowiecki.smartalarm.data.main.alarmarming.FirebaseAlarmArmingService
import pl.rmakowiecki.smartalarm.feature.main.alarmarming.AlarmArmingInteractor
import pl.rmakowiecki.smartalarm.feature.main.alarmarming.AlarmArmingPresenter
import pl.rmakowiecki.smartalarm.feature.main.alarmarming.AlarmArmingViewState
import pl.rmakowiecki.smartalarm.feature.main.alarmarming.AlarmArmingViewStateReducer

@RunWith(MockitoJUnitRunner::class)
class AlarmArmingInteractorTest {

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    @Mock lateinit var reducer: AlarmArmingViewStateReducer
    @Mock lateinit var service: FirebaseAlarmArmingService
    @InjectMocks lateinit var interactor: AlarmArmingInteractor

    private lateinit var viewRobot: AlarmArmingViewRobot

    @Before
    fun setUp() {
        viewRobot = AlarmArmingViewRobot(AlarmArmingPresenter(interactor))
    }

    @Test
    fun `correctly creates new view state after clicking the alarm arming button`() {

        with(viewRobot) {

            alarmDisarmingIntent.onNext(Unit)
            alarmDisarmingIntent.onNext(Unit)

            assertViewStatesRendered(
                    AlarmArmingViewState(
                            isInitializing = true,
                            isAlarmArmed = false
                    ),
                    AlarmArmingViewState(
                            isInitializing = false,
                            isAlarmArmed = false
                    ),
                    AlarmArmingViewState(
                            isInitializing = false,
                            isAlarmArmed = true
                    )
            )
        }
    }
}