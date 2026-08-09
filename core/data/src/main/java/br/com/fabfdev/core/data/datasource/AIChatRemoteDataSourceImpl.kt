package br.com.fabfdev.core.data.datasource

import br.com.fabfdev.core.data.api.AIAPIService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AIChatRemoteDataSourceImpl/* @Inject constructor*/(
    private val aiApiService: AIAPIService,
    private val dispatcher: CoroutineDispatcher
) : AIChatRemoteDataSource {

    override suspend fun sendPrompt(stack: String, question: String): String? =
        withContext(dispatcher) {
            aiApiService.sendPrompt(stack, question)
        }

}