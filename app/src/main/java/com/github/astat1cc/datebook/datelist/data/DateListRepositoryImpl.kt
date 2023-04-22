package com.github.astat1cc.datebook.datelist.data

import com.github.astat1cc.datebook.core.database.model.DateItemDb
import com.github.astat1cc.datebook.core.models.domain.DateItem
import com.github.astat1cc.datebook.datelist.data.local.dao.DateListDao
import com.github.astat1cc.datebook.datelist.domain.DateListRepository
import com.github.astat1cc.datebook.datelist.domain.models.DateListItemDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DateListRepositoryImpl(private val dao: DateListDao) : DateListRepository {

    override suspend fun fetchDateList(
        dateTimestampFrom: Long,
        dateTimestampTo: Long
    ): Flow<List<DateListItemDomain>> =
        dao.fetchDateList(dateTimestampFrom, dateTimestampTo).map { dateListDb ->
            dateListDb.map { dateItemDb ->
                dateItemDb.toListItemDomain()
            }
        }

    override suspend fun saveNewDate(date: DateItem) {
        dao.saveDate(DateItemDb.fromDomain(date))
    }
}