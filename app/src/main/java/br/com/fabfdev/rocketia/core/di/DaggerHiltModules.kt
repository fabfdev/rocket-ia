package br.com.fabfdev.rocketia.core.di

import android.content.Context
import androidx.room.Room
import br.com.fabfdev.rocketia.data.datasource.AIChatLocalDataSource
import br.com.fabfdev.rocketia.data.datasource.AIChatLocalDataSourceImpl
import br.com.fabfdev.rocketia.data.datasource.AIChatRemoteDataSource
import br.com.fabfdev.rocketia.data.datasource.AIChatRemoteDataSourceImpl
import br.com.fabfdev.rocketia.data.local.database.AIChatHistoryDao
import br.com.fabfdev.rocketia.data.local.database.ROCKET_AI_DATABASE_NAME
import br.com.fabfdev.rocketia.data.local.database.RocketAIDatabase
import br.com.fabfdev.rocketia.data.local.preferences.UserSettingsDataStorePreferencesImpl
import br.com.fabfdev.rocketia.data.local.preferences.UserSettingsPreferences
import br.com.fabfdev.rocketia.data.remote.api.AIAPIService
import br.com.fabfdev.rocketia.data.remote.api.AIGeminiAPIServiceImpl
import br.com.fabfdev.rocketia.data.repository.AIChatRepositoryImpl
import br.com.fabfdev.rocketia.domain.repository.AIChatRepository
import br.com.fabfdev.rocketia.domain.usecase.ChangeStackUseCase
import br.com.fabfdev.rocketia.domain.usecase.CheckHasSelectedStackUseCase
import br.com.fabfdev.rocketia.domain.usecase.GetAIChatBySelectedStackUseCase
import br.com.fabfdev.rocketia.domain.usecase.GetSelectedStackUseCase
import br.com.fabfdev.rocketia.domain.usecase.SendUserQuestionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IODispatcher

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAIAPIService(): AIAPIService = AIGeminiAPIServiceImpl()

    @Provides
    @Singleton
    fun provideUserSettingsPreferences(
        @ApplicationContext context: Context
    ): UserSettingsPreferences =
        UserSettingsDataStorePreferencesImpl(context)

    @Provides
    @Singleton
    fun provideRocketAIDatabase(
        @ApplicationContext context: Context
    ): RocketAIDatabase =
        Room.databaseBuilder(
            context,
            RocketAIDatabase::class.java,
            ROCKET_AI_DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideAIChatHistoryDao(
        rocketAIDatabase: RocketAIDatabase
    ): AIChatHistoryDao = rocketAIDatabase.aiChatHistoryDao()

    @Provides
    @Singleton
    @IODispatcher
    fun provideIODispatcher() = Dispatchers.IO

    @Provides
    @Singleton
    fun provideAIChatLocalDataSource(
        @IODispatcher ioDispatcher: CoroutineDispatcher,
        aiChatHistoryDao: AIChatHistoryDao,
        userSettingsPreferences: UserSettingsPreferences
    ): AIChatLocalDataSource =
        AIChatLocalDataSourceImpl(
            aiChatHistoryDao,
            userSettingsPreferences,
            ioDispatcher,
        )

    @Provides
    @Singleton
    fun provideAIChatRemoteDataSource(
        @IODispatcher ioDispatcher: CoroutineDispatcher,
        aiApiService: AIAPIService,
    ): AIChatRemoteDataSource =
        AIChatRemoteDataSourceImpl(
            aiApiService,
            ioDispatcher,
        )

    @Provides
    @Singleton
    fun provideAIChatRepository(
        aiChatLocalDataSource: AIChatLocalDataSource,
        aiChatRemoteDataSource: AIChatRemoteDataSource
    ): AIChatRepository =
        AIChatRepositoryImpl(
            aiChatLocalDataSource,
            aiChatRemoteDataSource
        )

}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBindsModule {

    /*@Binds
    abstract fun bindAIApiService(
        aiGeminiAPIServiceImpl: AIGeminiAPIServiceImpl
    ): AIAPIService*/

    /*
    Usar @ApplicationContext no parametro do contexto na classe implementada
    @Binds
    abstract fun bindUserSettingsPreferences(
        userSettingsDataStorePreferencesImpl: UserSettingsDataStorePreferencesImpl
    ): UserSettingsPreferences*/

}

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
