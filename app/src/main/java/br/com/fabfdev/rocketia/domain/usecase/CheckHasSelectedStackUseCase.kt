package br.com.fabfdev.rocketia.domain.usecase

import br.com.fabfdev.rocketia.domain.repository.AIChatRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class CheckHasSelectedStackUseCase/* @Inject constructor*/(
    private val repository: AIChatRepository
) {

    suspend operator fun invoke(): Boolean {
        return repository.selectedStack.firstOrNull()?.isNotEmpty() == true
    }

}