package br.com.fabfdev.rocketia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fabfdev.rocketia.domain.model.AIChatText
import br.com.fabfdev.rocketia.domain.usecase.GetAIChatBySelectedStackUseCase
import br.com.fabfdev.rocketia.domain.usecase.GetSelectedStackUseCase
import br.com.fabfdev.rocketia.ui.event.AIChatHistoryEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AIChatHistoryViewModel(
    getSelectedStackUseCase: GetSelectedStackUseCase,
    private val getAIChatBySelectedStackUseCase: GetAIChatBySelectedStackUseCase
) : ViewModel() {

    val selectedStack: StateFlow<String?> = getSelectedStackUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )
    private val _aiChatHistoryBySelectedStack: MutableStateFlow<List<AIChatText>> =
        MutableStateFlow(emptyList())
    val aiChatHistoryBySelectedStack: StateFlow<List<AIChatText>> =
        _aiChatHistoryBySelectedStack.asStateFlow()

    fun onEvent(event: AIChatHistoryEvent) {
        when (event) {
            is AIChatHistoryEvent.GetAIChatHistoryBySelectedStack -> getAIChatHistoryBySelectedStack(
                event.stack
            )
        }
    }

    private fun getAIChatHistoryBySelectedStack(stack: String) {
        viewModelScope.launch {
            val aiChatBySelectedStack = getAIChatBySelectedStackUseCase(stack)
            _aiChatHistoryBySelectedStack.update { aiChatBySelectedStack }
        }
    }

}