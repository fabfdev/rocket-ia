package br.com.fabfdev.core.domain.usecase

import br.com.fabfdev.core.domain.repository.AIChatRepository

class SendUserQuestionUseCase/* @Inject constructor*/(
    private val repository: AIChatRepository
) {

    suspend operator fun invoke(question: String) {
        repository.sendUserQuestion(question = question)
    }

}