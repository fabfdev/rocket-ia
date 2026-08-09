package br.com.fabfdev.feature.aichat.di

import br.com.fabfdev.feature.aichat.viewmodel.AIChatHistoryViewModel
import br.com.fabfdev.feature.aichat.viewmodel.AIChatViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val aiChatModule = module {
    viewModelOf(::AIChatViewModel)
    viewModelOf(::AIChatHistoryViewModel)
}