package br.com.fabfdev.core.domain.di

import br.com.fabfdev.core.domain.repository.AIChatRepository
import br.com.fabfdev.core.domain.usecase.ChangeStackUseCase
import br.com.fabfdev.core.domain.usecase.CheckHasSelectedStackUseCase
import br.com.fabfdev.core.domain.usecase.GetAIChatBySelectedStackUseCase
import br.com.fabfdev.core.domain.usecase.GetSelectedStackUseCase
import br.com.fabfdev.core.domain.usecase.SendUserQuestionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    fun provideChangeStackUseCase(
        aiChatRepository: AIChatRepository
    ): ChangeStackUseCase = ChangeStackUseCase(aiChatRepository)

    @Provides
    fun provideCheckHasSelectedStackUseCase(
        aiChatRepository: AIChatRepository
    ): CheckHasSelectedStackUseCase = CheckHasSelectedStackUseCase(aiChatRepository)

    @Provides
    fun provideGetAIChatBySelectedStackUseCase(
        aiChatRepository: AIChatRepository
    ): GetAIChatBySelectedStackUseCase = GetAIChatBySelectedStackUseCase(aiChatRepository)

    @Provides
    fun provideGetSelectedStackUseCase(
        aiChatRepository: AIChatRepository
    ): GetSelectedStackUseCase = GetSelectedStackUseCase(aiChatRepository)

    @Provides
    fun provideSendUserQuestionUseCase(
        aiChatRepository: AIChatRepository
    ): SendUserQuestionUseCase = SendUserQuestionUseCase(aiChatRepository)

}
