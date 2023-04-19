package com.github.astat1cc.datebook.datelist.di

import com.github.astat1cc.datebook.core.database.DatesDatabase
import com.github.astat1cc.datebook.datelist.data.DateListRepositoryImpl
import com.github.astat1cc.datebook.datelist.data.local.dao.DateListDao
import com.github.astat1cc.datebook.datelist.domain.DateListInteractor
import com.github.astat1cc.datebook.datelist.domain.DateListRepository
import com.github.astat1cc.datebook.datelist.presentation.DateListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dateListModule = module {
    single<DateListDao> {
        provideAlbumsDao(database = get())
    }
    single<DateListRepository> {
        DateListRepositoryImpl(dao = get())
    }
    single<DateListInteractor> {
        DateListInteractor.Impl(repository = get(), errorHandler = get(), dispatchers = get())
    }
    viewModel {
        DateListViewModel(interactor = get(), errorHandler = get())
    }
}

private fun provideAlbumsDao(database: DatesDatabase): DateListDao = database.dateListDao()