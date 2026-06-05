package br.com.fabfdev.rocketia.data.repository

import br.com.fabfdev.rocketia.data.datasource.AIChatLocalDataSource
import br.com.fabfdev.rocketia.data.datasource.AIChatRemoteDataSource
import br.com.fabfdev.rocketia.data.local.database.AIChatTextEntity
import br.com.fabfdev.rocketia.data.mapper.toDomain
import br.com.fabfdev.rocketia.domain.model.AIChatText
import br.com.fabfdev.rocketia.domain.model.AIChatTextType
import br.com.fabfdev.rocketia.domain.repository.AIChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class AIChatRepositoryImpl(
    private val aiChatLocalDataSource: AIChatLocalDataSource,
    private val aiChatRemoteDataSource: AIChatRemoteDataSource
) : AIChatRepository {
    override val selectedStack: Flow<String?>
        get() = aiChatLocalDataSource.selectedStack

    override val aiChatBySelectedStack: Flow<List<AIChatText>>
        get() = aiChatLocalDataSource
            .aiCurrentChatBySelectedStack
            .map { currentChatEntity ->
                currentChatEntity.toDomain()
            }

    override suspend fun sendUserQuestion(question: String) {
        val stack = selectedStack.firstOrNull().orEmpty()
        val answer = aiChatRemoteDataSource.sendPrompt(stack = stack, question = question)
        answer?.let {
            aiChatLocalDataSource.insertAIChatConversation(
                question = createUserQuestionEntity(question, stack),
                answer = createAIAnswer(question, stack)
            )
        }
    }

    override suspend fun changeStack(stack: String) {
        aiChatLocalDataSource.changeSelectedStack(stack)
    }

    private fun createUserQuestionEntity(question: String, stack: String) = AIChatTextEntity(
        stack = stack,
        text = question,
        from = AIChatTextType.USER_QUESTION.name,
        datetime = System.currentTimeMillis()
    )

    private fun createAIAnswer(question: String, stack: String) = AIChatTextEntity(
        stack = stack,
        text = question,
        from = AIChatTextType.AI_ANSWER.name,
        datetime = System.currentTimeMillis()
    )
}