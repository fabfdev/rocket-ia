package br.com.fabfdev.core.domain.usecase

import br.com.fabfdev.core.domain.repository.AIChatRepository
import kotlinx.coroutines.flow.Flow

class GetSelectedStackUseCase/* @Inject constructor*/(
    private val repository: AIChatRepository
) {

    operator fun invoke(): Flow<String?> = repository.selectedStack

}