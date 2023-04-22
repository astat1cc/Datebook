package com.github.astat1cc.datebook.core.models.domain

data class DateItem(
    val id: Int,
    val dateStart: Long,
    val dateFinish: Long?,
    val name: String,
    val description: String
)