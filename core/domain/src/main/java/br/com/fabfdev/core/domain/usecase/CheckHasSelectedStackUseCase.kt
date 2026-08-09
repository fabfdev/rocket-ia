package br.com.fabfdev.core.domain.usecase

import br.com.fabfdev.core.domain.repository.AIChatRepository
import kotlinx.coroutines.flow.firstOrNull

class CheckHasSelectedStackUseCase/* @Inject constructor*/(
    private val repository: AIChatRepository
) {

    suspend operator fun invoke(): Boolean {
        return repository.selectedStack.firstOrNull()?.isNotEmpty() == true
    }

}