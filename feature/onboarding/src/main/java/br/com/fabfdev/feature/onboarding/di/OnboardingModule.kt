package br.com.fabfdev.feature.onboarding.di

import br.com.fabfdev.feature.onboarding.viewmodel.WelcomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val onboardingModule = module {
    viewModelOf(::WelcomeViewModel)
}