package com.github.astat1cc.datebook

import android.app.Application
import com.github.astat1cc.datebook.core.di.coreModule
import com.github.astat1cc.datebook.datelist.di.dateListModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DatebookApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@DatebookApp)
            modules(coreModule, dateListModule)
        }
    }
}