package com.github.astat1cc.datebook.datelist.presentation.models

import com.github.astat1cc.datebook.core.util.DateFormatUtil
import com.github.astat1cc.datebook.datelist.domain.models.DateListItemDomain

data class DateListItemUi(
    val id: Int,
    val dateStart: String,
    val name: String
) {

    companion object {

        fun fromDomain(date: DateListItemDomain, dateFormatUtil: DateFormatUtil) = with(date) {
            DateListItemUi(id, dateFormatUtil.getDateWithTimeFrom(dateStart), name)
        }
    }
}