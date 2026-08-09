package br.com.fabfdev.feature.stackselection.event

sealed interface ChooseStackUiEvent {
    data class SelectedStack(val selectedStackName: String, val selectedStackChipId: Int) :
        ChooseStackUiEvent
}