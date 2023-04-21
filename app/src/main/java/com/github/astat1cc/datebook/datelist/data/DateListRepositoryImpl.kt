package com.github.astat1cc.datebook.datelist.data

import com.github.astat1cc.datebook.datelist.data.local.dao.DateListDao
import com.github.astat1cc.datebook.datelist.domain.DateListRepository
import com.github.astat1cc.datebook.datelist.domain.models.DateListItemDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DateListRepositoryImpl(private val dao: DateListDao) : DateListRepository {

    override suspend fun fetchDateList(date: String): Flow<List<DateListItemDomain>> =
        dao.fetchDateList().map { dateListDb ->
            dateListDb.map { dateItemDb ->
                dateItemDb.toListItemDomain()
            }
        }
}