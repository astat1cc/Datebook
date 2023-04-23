package com.github.astat1cc.datebook.core.models.ui

import com.github.astat1cc.datebook.core.models.domain.DateItem
import com.github.astat1cc.datebook.core.util.DateFormatUtil

data class DateItemUi(
    val id: Int,
    val dateStart: String,
    val dateFinish: String?,
    val name: String,
    val description: String
) {

    fun toDomain(dateFormatUtil: DateFormatUtil) = DateItem(
        id,
        dateFormatUtil.getTimestampInMillisFrom(dateStart),
        dateFinish?.let { dateFormatUtil.getTimestampInMillisFrom(dateFinish) },
        name,
        description
    )

    companion object {

        fun fromDomain(dateDomain: DateItem, dateFormatUtil: DateFormatUtil) = with(dateDomain) {
            DateItemUi(
                id,
                dateFormatUtil.getDateWithTimeFrom(dateStart),
                dateFinish?.let { dateFormatUtil.getDateWithTimeFrom(dateFinish) },
                name,
                description
            )
        }
    }
}