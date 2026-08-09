package br.com.fabfdev.feature.onboarding.event

sealed interface WelcomeUiEvent {
    object CheckHasSelectedStack: WelcomeUiEvent
}