package br.com.fabfdev.rocketia.domain.repository

import br.com.fabfdev.rocketia.domain.model.AIChatText
import kotlinx.coroutines.flow.Flow

interface AIChatRepository {
    val selectedStack: Flow<String?>
    val aiChatBySelectedStack: Flow<List<AIChatText>>
    suspend fun sendUserQuestion(question: String)
    suspend fun changeStack(stack: String)
    suspend fun getAIChatByStack(stack: String): List<AIChatText>
}