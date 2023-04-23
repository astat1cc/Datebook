package com.github.astat1cc.datebook.datedetails.domain

import com.github.astat1cc.datebook.core.models.domain.DateItem

interface DateDetailsRepository {

    suspend fun fetchDate(id: Int): DateItem
}