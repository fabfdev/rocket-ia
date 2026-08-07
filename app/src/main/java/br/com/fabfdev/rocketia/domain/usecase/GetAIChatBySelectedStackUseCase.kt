package br.com.fabfdev.rocketia.domain.usecase

import br.com.fabfdev.rocketia.domain.model.AIChatText
import br.com.fabfdev.rocketia.domain.repository.AIChatRepository
import kotlinx.coroutines.flow.Flow

class GetAIChatBySelectedStackUseCase/* @Inject constructor*/(
    private val repository: AIChatRepository
) {

    operator fun invoke(): Flow<List<AIChatText>> =
        repository.aiChatBySelectedStack

    suspend operator fun invoke(stack: String): List<AIChatText> =
        repository.getAIChatByStack(stack)

}