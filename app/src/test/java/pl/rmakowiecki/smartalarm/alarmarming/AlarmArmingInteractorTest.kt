package pl.rmakowiecki.smartalarm.alarmarming

import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner
import pl.rmakowiecki.smartalarm.RxSchedulersOverrideRule
import pl.rmakowiecki.smartalarm.data.main.alarmarming.FirebaseAlarmArmingService
import pl.rmakowiecki.smartalarm.feature.main.alarmarming.AlarmArmingInteractor
import pl.rmakowiecki.smartalarm.feature.main.alarmarming.AlarmArmingPresenter
import pl.rmakowiecki.smartalarm.feature.main.alarmarming.AlarmArmingViewStateReducer

@RunWith(MockitoJUnitRunner::class)
class AlarmArmingInteractorTest {

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    @Spy lateinit var reducer: AlarmArmingViewStateReducer
    @Spy lateinit var service: FirebaseAlarmArmingService
    @InjectMocks lateinit var interactor: AlarmArmingInteractor

    private lateinit var viewRobot: AlarmArmingViewRobot

    @Before
    fun setUp() {
        viewRobot = AlarmArmingViewRobot(AlarmArmingPresenter(interactor))
    }

}