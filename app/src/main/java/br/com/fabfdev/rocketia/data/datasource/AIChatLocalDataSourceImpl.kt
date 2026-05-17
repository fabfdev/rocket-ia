package br.com.fabfdev.rocketia.data.datasource

import br.com.fabfdev.rocketia.data.local.database.AIChatHistoryDao
import br.com.fabfdev.rocketia.data.local.database.AIChatTextEntity
import br.com.fabfdev.rocketia.data.local.preferences.UserSettingsPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class AIChatLocalDataSourceImpl(
    private val aiChatHistoryDao: AIChatHistoryDao,
    private val userSettingsPreferences: UserSettingsPreferences,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : AIChatLocalDataSource {

    @OptIn(ExperimentalCoroutinesApi::class)
    override val aiCurrentChatBySelectedStack: Flow<List<AIChatTextEntity>>
        get() = userSettingsPreferences.selectedStack.flatMapLatest { selectedStack ->
            aiChatHistoryDao.getAllByStack(selectedStack)
        }.flowOn(dispatcher)

    override suspend fun insertAIChatConversation(
        question: AIChatTextEntity,
        answer: AIChatTextEntity
    ) {
        withContext(dispatcher) {
            aiChatHistoryDao.insertAll(question, answer)
        }
    }

    override val selectedStack: Flow<String>
        get() = userSettingsPreferences
            .selectedStack
            .flowOn(dispatcher)

    override suspend fun changeSelectedStack(stack: String) {
        withContext(dispatcher) {
            userSettingsPreferences.changeSelectedStack(stack)
        }
    }

    override val firstLaunch: Flow<Boolean>
        get() = userSettingsPreferences
            .firstLaunch
            .flowOn(dispatcher)

    override suspend fun changeFirstLaunch() {
        withContext(dispatcher) {
            userSettingsPreferences.changeFirstLaunch()
        }
    }
}