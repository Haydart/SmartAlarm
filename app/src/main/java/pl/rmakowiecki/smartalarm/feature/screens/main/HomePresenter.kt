package pl.rmakowiecki.smartalarm.feature.screens.main

import pl.rmakowiecki.smartalarm.base.mvi.MviPresenter
import javax.inject.Inject

class HomePresenter @Inject constructor(
        private val interactor: HomeInteractor
) : MviPresenter<HomeView, HomeViewState>() {

    override fun bindIntents() = with(interactor) {
        attachTabSwitchIntent(bindIntent(HomeView::tabSwitchIntent))

        subscribeViewState(getViewStateObservable(), HomeView::render)
    }
}