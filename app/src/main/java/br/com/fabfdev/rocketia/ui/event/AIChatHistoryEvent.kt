package br.com.fabfdev.rocketia.ui.event

sealed interface AIChatHistoryEvent {
    data class SelectedStack(val selectedStackName: String, val selectedStackChipId: Int): AIChatHistoryEvent
}