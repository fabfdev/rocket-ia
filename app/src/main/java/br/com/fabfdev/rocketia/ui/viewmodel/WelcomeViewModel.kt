package br.com.fabfdev.rocketia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fabfdev.rocketia.domain.usecase.CheckHasSelectedStackUseCase
import br.com.fabfdev.rocketia.ui.event.WelcomeUiEvent
import br.com.fabfdev.rocketia.ui.state.WelcomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//@HiltViewModel
class WelcomeViewModel/* @Inject constructor*/(
    private val checkHasSelectedStackUseCase: CheckHasSelectedStackUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<WelcomeUiState> = MutableStateFlow(WelcomeUiState())
    val uiState: StateFlow<WelcomeUiState> = _uiState.asStateFlow()

    fun onEvent(event: WelcomeUiEvent) {
        when (event) {
            WelcomeUiEvent.CheckHasSelectedStack -> checkHasSelectedStack()
        }
    }

    private fun checkHasSelectedStack() {
        viewModelScope.launch {
            val hasSelectedStack = checkHasSelectedStackUseCase()
            _uiState.update { currentUiState -> currentUiState.copy(hasSelectedStack = hasSelectedStack) }
        }
    }

}