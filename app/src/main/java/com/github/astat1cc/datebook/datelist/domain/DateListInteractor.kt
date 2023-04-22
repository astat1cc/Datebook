package com.github.astat1cc.datebook.datelist.domain

import android.util.Log
import com.github.astat1cc.datebook.core.models.domain.DateItem
import com.github.astat1cc.datebook.core.models.domain.FetchResult
import com.github.astat1cc.datebook.core.util.AppErrorHandler
import com.github.astat1cc.datebook.core.util.DateFormatUtil
import com.github.astat1cc.datebook.core.util.DispatchersProvider
import com.github.astat1cc.datebook.datelist.domain.models.DateListItemDomain
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

interface DateListInteractor {

    suspend fun fetchDateList(
        dateTimestampFrom: Long,
        dateTimestampTo: Long
    ): Flow<FetchResult<Map<String, List<DateListItemDomain>>>>

    suspend fun saveNewDate(date: DateItem)

    class Impl(
        private val repository: DateListRepository,
        private val errorHandler: AppErrorHandler,
        private val dispatchers: DispatchersProvider,
        private val dateFormatUtil: DateFormatUtil
    ) : DateListInteractor {

        override suspend fun fetchDateList(
            dateTimestampFrom: Long,
            dateTimestampTo: Long
        ): Flow<FetchResult<Map<String, List<DateListItemDomain>>>> =
            withContext(dispatchers.io()) {
                repository.fetchDateList(dateTimestampFrom, dateTimestampTo).map { dateList ->
                    val map = dateList
                        .groupBy { date ->
                            dateFormatUtil.getOnlyHourFrom(date.dateStart)
                        }
                        .mapValues { entry ->
                            entry.value.sortedBy { date ->
                                date.dateStart
                            }
                        }
                    delay(200L)
                    try {
                        FetchResult.Success(data = map)
                    } catch (e: Exception) {
                        FetchResult.Fail(error = errorHandler.getErrorTypeOf(e))
                    }
                }
            }

        override suspend fun saveNewDate(date: DateItem) {
            withContext(dispatchers.io()) {
                repository.saveNewDate(date)
            }
        }
    }
}