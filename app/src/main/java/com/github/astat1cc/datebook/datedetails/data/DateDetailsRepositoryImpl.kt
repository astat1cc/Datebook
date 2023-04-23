package com.github.astat1cc.datebook.datedetails.data

import com.github.astat1cc.datebook.core.models.domain.DateItem
import com.github.astat1cc.datebook.datedetails.data.local.DateDetailsDao
import com.github.astat1cc.datebook.datedetails.domain.DateDetailsRepository

class DateDetailsRepositoryImpl(private val dao: DateDetailsDao) : DateDetailsRepository {

    override suspend fun fetchDate(id: Int): DateItem = dao.fetchDate(id).toDomain()
}