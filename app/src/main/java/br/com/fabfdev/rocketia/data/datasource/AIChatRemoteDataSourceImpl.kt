package br.com.fabfdev.rocketia.data.datasource

import br.com.fabfdev.rocketia.data.remote.api.AIAPIService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AIChatRemoteDataSourceImpl @Inject constructor(
    private val aiApiService: AIAPIService,
    private val dispatcher: CoroutineDispatcher
) : AIChatRemoteDataSource {

    override suspend fun sendPrompt(stack: String, question: String): String? =
        withContext(dispatcher) {
            aiApiService.sendPrompt(stack, question)
        }

}