package br.com.fabfdev.core.data.datasource

import br.com.fabfdev.core.data.local.database.AIChatHistoryDao
import br.com.fabfdev.core.data.local.database.AIChatTextEntity
import br.com.fabfdev.core.data.local.preferences.UserSettingsPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class AIChatLocalDataSourceImpl/* @Inject constructor*/(
    private val aiChatHistoryDao: AIChatHistoryDao,
    private val userSettingsPreferences: UserSettingsPreferences,
    private val dispatcher: CoroutineDispatcher,
) : AIChatLocalDataSource {

    @OptIn(ExperimentalCoroutinesApi::class)
    override val aiCurrentChatBySelectedStack: Flow<List<AIChatTextEntity>>
        get() = userSettingsPreferences.selectedStack.flatMapLatest { selectedStack ->
            aiChatHistoryDao.getAllByStackFlow(selectedStack.orEmpty())
        }.flowOn(dispatcher)

    override suspend fun insertAIChatConversation(
        question: AIChatTextEntity,
        answer: AIChatTextEntity
    ) {
        withContext(dispatcher) {
            aiChatHistoryDao.insertAll(question, answer)
        }
    }

    override val selectedStack: Flow<String?>
        get() = userSettingsPreferences
            .selectedStack
            .flowOn(dispatcher)

    override suspend fun changeSelectedStack(stack: String) {
        withContext(dispatcher) {
            userSettingsPreferences.changeSelectedStack(stack)
        }
    }

    override suspend fun getAIChatByStack(stack: String): List<AIChatTextEntity> {
        return withContext(dispatcher) {
            aiChatHistoryDao.getAllByStack(stack)
        }
    }
}