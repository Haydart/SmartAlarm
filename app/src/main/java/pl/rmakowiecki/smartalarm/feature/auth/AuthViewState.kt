package pl.rmakowiecki.smartalarm.feature.auth

import pl.rmakowiecki.smartalarm.base.Contracts
import pl.rmakowiecki.smartalarm.domain.auth.AuthMode

data class AuthViewState(
        val emailInputText: String = "",
        val emailInputError: String? = null,
        val passwordInputText: String = "",
        val passwordInputError: String? = null,
        val repeatPasswordInputText: String = "",
        val repeatPasswordInputError: String? = null,
        val credentialsSubmitButtonEnabled: Boolean = false,
        val screenPerspective: AuthMode = AuthMode.LOGIN,
        val isLoading: Boolean = false,
        val isShowingSuccess: Boolean = false,
        val generalError: String? = null
) : Contracts.ViewState