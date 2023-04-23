package com.github.astat1cc.datebook.datedetails.domain

import com.github.astat1cc.datebook.core.models.domain.DateItem
import com.github.astat1cc.datebook.core.models.domain.FetchResult
import com.github.astat1cc.datebook.core.util.AppErrorHandler
import com.github.astat1cc.datebook.core.util.DispatchersProvider
import kotlinx.coroutines.withContext

interface DateDetailsInteractor {

    suspend fun fetchDate(id: Int): FetchResult<DateItem>

    class Impl(
        private val repository: DateDetailsRepository,
        private val dispatchers: DispatchersProvider,
        private val errorHandler: AppErrorHandler
    ) : DateDetailsInteractor {

        override suspend fun fetchDate(id: Int): FetchResult<DateItem> =
            withContext(dispatchers.io()) {
                try {
                    FetchResult.Success(
                        data = repository.fetchDate(id)
                    )
                } catch (e: Exception) {
                    FetchResult.Fail(error = errorHandler.getErrorTypeOf(e))
                }
            }
    }
}