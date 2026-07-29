package br.com.fabfdev.rocketia.ui.event

sealed interface WelcomeUiEvent {
    object CheckHasSelectedStack: WelcomeUiEvent
}