package com.github.astat1cc.datebook.datelist.domain

import com.github.astat1cc.datebook.core.models.domain.FetchResult
import com.github.astat1cc.datebook.core.util.AppErrorHandler
import com.github.astat1cc.datebook.core.util.DispatchersProvider
import com.github.astat1cc.datebook.datelist.domain.models.DateListItemDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

interface DateListInteractor {

    suspend fun fetchDateList(): Flow<FetchResult<List<DateListItemDomain>>>

    class Impl(
        private val repository: DateListRepository,
        private val errorHandler: AppErrorHandler,
        private val dispatchers: DispatchersProvider
    ) : DateListInteractor {

        override suspend fun fetchDateList(): Flow<FetchResult<List<DateListItemDomain>>> =
            withContext(dispatchers.io()) {
                repository.fetchDateList().map { dateList ->
                    try {
                        FetchResult.Success(data = dateList)
                    } catch (e: Exception) {
                        FetchResult.Fail(error = errorHandler.getErrorTypeOf(e))
                    }
                }
            }
    }
}