package br.com.fabfdev.rocketia.ui.event

sealed interface ChooseStackUiEvent {
    data class SelectedStack(val selectedStackName: String, val selectedStackChipId: Int) :
        ChooseStackUiEvent
}