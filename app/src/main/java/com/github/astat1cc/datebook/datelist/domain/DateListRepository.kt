package com.github.astat1cc.datebook.datelist.domain

import com.github.astat1cc.datebook.datelist.domain.models.DateListItemDomain
import kotlinx.coroutines.flow.Flow

interface DateListRepository {

    suspend fun fetchDateList(date: String): Flow<List<DateListItemDomain>>
}