package br.com.fabfdev.rocketia.domain.usecase

import br.com.fabfdev.rocketia.domain.repository.AIChatRepository
import kotlinx.coroutines.flow.firstOrNull

class CheckHasSelectedStackUseCase(
    private val repository: AIChatRepository
) {

    suspend operator fun invoke(): Boolean {
        return repository.selectedStack.firstOrNull()?.isNotEmpty() == true
    }

}