package com.github.astat1cc.datebook.core.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatchersProvider {

    fun io(): CoroutineDispatcher

    class Impl : DispatchersProvider {

        override fun io(): CoroutineDispatcher = Dispatchers.IO
    }
}