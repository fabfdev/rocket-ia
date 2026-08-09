package br.com.fabfdev.core.data.di

import androidx.room.Room
import br.com.fabfdev.core.data.api.AIAPIService
import br.com.fabfdev.core.data.api.AIGeminiAPIServiceImpl
import br.com.fabfdev.core.data.datasource.AIChatLocalDataSource
import br.com.fabfdev.core.data.datasource.AIChatLocalDataSourceImpl
import br.com.fabfdev.core.data.datasource.AIChatRemoteDataSource
import br.com.fabfdev.core.data.datasource.AIChatRemoteDataSourceImpl
import br.com.fabfdev.core.data.local.database.AIChatHistoryDao
import br.com.fabfdev.core.data.local.database.ROCKET_AI_DATABASE_NAME
import br.com.fabfdev.core.data.local.database.RocketAIDatabase
import br.com.fabfdev.core.data.local.preferences.UserSettingsDataStorePreferencesImpl
import br.com.fabfdev.core.data.local.preferences.UserSettingsPreferences
import br.com.fabfdev.core.data.repository.AIChatRepositoryImpl
import br.com.fabfdev.core.domain.repository.AIChatRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single<CoroutineDispatcher>(named("IO")) { Dispatchers.IO }
    single<AIAPIService> { AIGeminiAPIServiceImpl() }
    single<UserSettingsPreferences> { UserSettingsDataStorePreferencesImpl(androidApplication()) }
    single<RocketAIDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            RocketAIDatabase::class.java,
            ROCKET_AI_DATABASE_NAME
        ).build()
    }
    single<AIChatHistoryDao> { get<RocketAIDatabase>().aiChatHistoryDao() }
    single<AIChatLocalDataSource> {
        AIChatLocalDataSourceImpl(
            get(),
            get(),
            get(named("IO"))
        )
    }
    single<AIChatRemoteDataSource> {
        AIChatRemoteDataSourceImpl(
            get(),
            get(named("IO"))
        )
    }
    single<AIChatRepository> {
        AIChatRepositoryImpl(
            get(),
            get()
        )
    }
}
