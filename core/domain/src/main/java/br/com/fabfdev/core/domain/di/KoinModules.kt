package br.com.fabfdev.core.domain.di

import br.com.fabfdev.core.domain.usecase.ChangeStackUseCase
import br.com.fabfdev.core.domain.usecase.CheckHasSelectedStackUseCase
import br.com.fabfdev.core.domain.usecase.GetAIChatBySelectedStackUseCase
import br.com.fabfdev.core.domain.usecase.GetSelectedStackUseCase
import br.com.fabfdev.core.domain.usecase.SendUserQuestionUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { ChangeStackUseCase(get()) }
    factory { CheckHasSelectedStackUseCase(get()) }
    factory { GetAIChatBySelectedStackUseCase(get()) }
    factory { GetSelectedStackUseCase(get()) }
    factory { SendUserQuestionUseCase(get()) }
}
