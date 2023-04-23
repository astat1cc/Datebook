package com.github.astat1cc.datebook.datedetails.di

import com.github.astat1cc.datebook.core.database.DatesDatabase
import com.github.astat1cc.datebook.datedetails.data.DateDetailsRepositoryImpl
import com.github.astat1cc.datebook.datedetails.data.local.DateDetailsDao
import com.github.astat1cc.datebook.datedetails.domain.DateDetailsInteractor
import com.github.astat1cc.datebook.datedetails.domain.DateDetailsRepository
import com.github.astat1cc.datebook.datedetails.presentation.DateDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dateDetailsModule = module {
    single<DateDetailsDao> {
        provideDao(database = get())
    }
    single<DateDetailsRepository> {
        DateDetailsRepositoryImpl(dao = get())
    }
    single<DateDetailsInteractor> {
        DateDetailsInteractor.Impl(repository = get(), dispatchers = get(), errorHandler = get())
    }
    viewModel {
        DateDetailsViewModel(
            dateId = get(),
            interactor = get(),
            errorHandler = get(),
            dateFormatUtil = get()
        )
    }
}

private fun provideDao(database: DatesDatabase) = database.dateDetailsDao()