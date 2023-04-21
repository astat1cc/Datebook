package com.github.astat1cc.datebook.datelist.domain

import com.github.astat1cc.datebook.core.models.domain.FetchResult
import com.github.astat1cc.datebook.core.util.AppErrorHandler
import com.github.astat1cc.datebook.core.util.DateFormatUtil
import com.github.astat1cc.datebook.core.util.DispatchersProvider
import com.github.astat1cc.datebook.datelist.domain.models.DateListItemDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

interface DateListInteractor {

    suspend fun fetchDateList(date: String): Flow<FetchResult<Map<String, List<DateListItemDomain>>>>

    class Impl(
        private val repository: DateListRepository,
        private val errorHandler: AppErrorHandler,
        private val dispatchers: DispatchersProvider,
        private val dateFormatUtil: DateFormatUtil
    ) : DateListInteractor {

        override suspend fun fetchDateList(date: String): Flow<FetchResult<Map<String, List<DateListItemDomain>>>> =
            withContext(dispatchers.io()) {
                repository.fetchDateList(date = date).map { dateList ->
                    val map = dateList.groupBy { date ->
                        dateFormatUtil.getOnlyTimeFrom(date.dateStart)
                    }
                    try {
                        FetchResult.Success(data = map)
                    } catch (e: Exception) {
                        FetchResult.Fail(error = errorHandler.getErrorTypeOf(e))
                    }
                }
            }
    }
}