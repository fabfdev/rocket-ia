package br.com.fabfdev.rocketia

import android.app.Application
import br.com.fabfdev.core.data.di.dataModule
import br.com.fabfdev.core.domain.di.domainModule
import br.com.fabfdev.feature.aichat.di.aiChatModule
import br.com.fabfdev.feature.onboarding.di.onboardingModule
import br.com.fabfdev.feature.stackselection.di.stackSelectionModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

//@HiltAndroidApp
class RocketAIApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@RocketAIApplication)
            modules(
                dataModule,
                domainModule,
                onboardingModule,
                stackSelectionModule,
                aiChatModule
            )
        }
    }

}