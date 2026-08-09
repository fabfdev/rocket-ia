package br.com.fabfdev.feature.stackselection.di

import br.com.fabfdev.feature.stackselection.viewmodel.ChooseStackViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val stackSelectionModule = module {
    viewModelOf(::ChooseStackViewModel)
}