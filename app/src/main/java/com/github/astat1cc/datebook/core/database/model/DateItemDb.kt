package com.github.astat1cc.datebook.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.astat1cc.datebook.datelist.domain.models.DateListItemDomain

@Entity(tableName = DateItemDb.TABLE_NAME)
data class DateItemDb(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "date_start") val dateStart: String,
    @ColumnInfo(name = "date_finish") val dateFinish: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String
) {

    fun toListItemDomain() = DateListItemDomain(id, dateStart, name)

    companion object {

        const val TABLE_NAME = "dates"

//        fun fromDomain(date: DateListItemDomain) =
//            with(date) { DateItemDb }
    }
}