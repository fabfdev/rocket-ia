package br.com.fabfdev.core.data.datasource

interface AIChatRemoteDataSource {
    suspend fun sendPrompt(stack: String, question: String): String?
}