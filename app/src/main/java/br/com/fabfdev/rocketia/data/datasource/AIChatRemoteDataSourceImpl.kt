package br.com.fabfdev.rocketia.data.datasource

import br.com.fabfdev.rocketia.data.api.AIAPIService

class AIChatRemoteDataSourceImpl(
    private val aiApiService: AIAPIService,
): AIChatRemoteDataSource {

    override suspend fun sendPrompt(stack: String, question: String): String? =
        aiApiService.sendPrompt(stack, question)

}