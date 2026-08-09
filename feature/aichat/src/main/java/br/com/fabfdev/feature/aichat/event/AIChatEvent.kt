package br.com.fabfdev.feature.aichat.event

sealed interface AIChatEvent {
    data class SendUserQuestionToAI(val question: String): AIChatEvent
}