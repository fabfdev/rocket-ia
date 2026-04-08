package br.com.fabfdev.rocketia.data.datasource

interface AIChatRemoteDataSource {
    suspend fun sendPrompt(stack: String, question: String): String?
}