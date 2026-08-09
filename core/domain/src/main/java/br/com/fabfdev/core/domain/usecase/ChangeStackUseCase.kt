package br.com.fabfdev.core.domain.usecase

import br.com.fabfdev.core.domain.repository.AIChatRepository

class ChangeStackUseCase/* @Inject constructor*/(
    private val repository: AIChatRepository
) {

    suspend operator fun invoke(stack: String) {
        repository.changeStack(stack = stack)
    }

}