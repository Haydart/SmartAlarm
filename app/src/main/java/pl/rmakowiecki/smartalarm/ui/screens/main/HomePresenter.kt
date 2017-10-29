package pl.rmakowiecki.smartalarm.ui.screens.main

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class HomePresenter @Inject constructor(
        private val interactor: HomeInteractor
) : MviPresenter<Home.View, HomeViewState>() {

    override fun bindIntents() = with(interactor) {
        attachTabSwitchIntent(bindIntent(Home.View::tabSwitchIntent))

        subscribeViewState(getViewStateObservable(), Home.View::render)
    }
}