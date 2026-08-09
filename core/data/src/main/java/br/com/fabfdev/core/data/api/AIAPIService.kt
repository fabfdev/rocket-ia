package br.com.fabfdev.core.data.api

interface AIAPIService {
    suspend fun sendPrompt(stack: String, question: String): String?
}