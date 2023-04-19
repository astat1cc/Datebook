package com.github.astat1cc.datebook.datelist.presentation.models

import com.github.astat1cc.datebook.datelist.domain.models.DateListItemDomain

data class DateListItemUi(
    val id: Int,
    val dateStart: String,
    val name: String
) {

    companion object {

        fun fromDomain(date: DateListItemDomain) = with(date) {
            DateListItemUi(id, dateStart, name)
        }
    }
}