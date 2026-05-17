package br.com.fabfdev.rocketia.data.remote.api

interface AIAPIService {
    suspend fun sendPrompt(stack: String, question: String): String?
}