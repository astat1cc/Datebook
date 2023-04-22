package com.github.astat1cc.datebook.datelist.domain

import com.github.astat1cc.datebook.core.models.domain.DateItem
import com.github.astat1cc.datebook.datelist.domain.models.DateListItemDomain
import kotlinx.coroutines.flow.Flow

interface DateListRepository {

    suspend fun fetchDateList(
        dateTimestampFrom: Long,
        dateTimestampTo: Long
    ): Flow<List<DateListItemDomain>>

    suspend fun saveNewDate(date: DateItem)
}