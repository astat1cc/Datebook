package com.github.astat1cc.datebook.core.di

import androidx.room.Room
import com.github.astat1cc.datebook.core.database.DatesDatabase
import com.github.astat1cc.datebook.core.util.AppErrorHandler
import com.github.astat1cc.datebook.core.util.AppResourceProvider
import com.github.astat1cc.datebook.core.util.DispatchersProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {
    single<DatesDatabase> {
        Room.databaseBuilder(
            androidContext(),
            DatesDatabase::class.java,
            DatesDatabase.DATABASE_NAME
        ).build()
    }
    single<DispatchersProvider> {
        DispatchersProvider.Impl()
    }
    single<AppResourceProvider> {
        AppResourceProvider.Impl(androidContext())
    }
    single<AppErrorHandler> {
        AppErrorHandler.Impl(resources = get())
    }
}