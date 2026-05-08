package br.com.fabfdev.rocketia.data.datasource

import br.com.fabfdev.rocketia.data.local.database.AIChatTextEntity
import kotlinx.coroutines.flow.Flow

interface AIChatLocalDataSource {
    val aiCurrentChatBySelectedStack: Flow<List<AIChatTextEntity>>
    suspend fun insertAIChatConversation(question: AIChatTextEntity, answer: AIChatTextEntity)
    val selectedStack: Flow<String>
    suspend fun changeSelectedStack(stack: String)
    val firstLaunch: Flow<Boolean>
    suspend fun changeFirstLaunch()
}