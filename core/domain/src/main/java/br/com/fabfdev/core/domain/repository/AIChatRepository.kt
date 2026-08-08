package br.com.fabfdev.core.domain.repository

import br.com.fabfdev.core.domain.model.AIChatText
import kotlinx.coroutines.flow.Flow

interface AIChatRepository {
    val selectedStack: Flow<String?>
    val aiChatBySelectedStack: Flow<List<AIChatText>>
    suspend fun sendUserQuestion(question: String)
    suspend fun changeStack(stack: String)
    suspend fun getAIChatByStack(stack: String): List<AIChatText>
}