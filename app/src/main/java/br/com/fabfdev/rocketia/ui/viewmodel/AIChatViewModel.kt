package br.com.fabfdev.rocketia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fabfdev.rocketia.domain.model.AIChatText
import br.com.fabfdev.rocketia.domain.usecase.GetAIChatBySelectedStackUseCase
import br.com.fabfdev.rocketia.domain.usecase.GetSelectedStackUseCase
import br.com.fabfdev.rocketia.domain.usecase.SendUserQuestionUseCase
import br.com.fabfdev.rocketia.ui.event.AIChatEvent
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AIChatViewModel(
    getSelectedStackUseCase: GetSelectedStackUseCase,
    getAIChatBySelectedStackUseCase: GetAIChatBySelectedStackUseCase,
    private val sendUserQuestionUseCase: SendUserQuestionUseCase,
) : ViewModel() {

    val selectedStack: StateFlow<String?> = getSelectedStackUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )
    val aiChatBySelectedStack: StateFlow<List<AIChatText>> = getAIChatBySelectedStackUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun onEvent(event: AIChatEvent) {
        when (event) {
            is AIChatEvent.SendUserQuestionToAI -> sendUserQuestionToAI(event.question)
        }
    }

    private fun sendUserQuestionToAI(question: String) {
        viewModelScope.launch {
            sendUserQuestionUseCase(question)
        }
    }

}