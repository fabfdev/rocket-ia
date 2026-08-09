package br.com.fabfdev.feature.aichat.event

sealed interface AIChatHistoryEvent {
    data class SelectedStack(val selectedStackName: String, val selectedStackChipId: Int): AIChatHistoryEvent
}