package br.com.fabfdev.rocketia.core.di

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

val domainModule = module { }

val uiModule = module { }