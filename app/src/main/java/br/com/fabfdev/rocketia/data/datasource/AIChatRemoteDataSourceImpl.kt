package br.com.fabfdev.rocketia.data.datasource

import br.com.fabfdev.rocketia.data.remote.api.AIAPIService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AIChatRemoteDataSourceImpl(
    private val aiApiService: AIAPIService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AIChatRemoteDataSource {

    override suspend fun sendPrompt(stack: String, question: String): String? =
        withContext(dispatcher) {
            aiApiService.sendPrompt(stack, question)
        }

}