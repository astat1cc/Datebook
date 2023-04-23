package com.github.astat1cc.datebook.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.astat1cc.datebook.core.models.domain.DateItem
import com.github.astat1cc.datebook.datelist.domain.models.DateListItemDomain

@Entity(tableName = DateItemDb.TABLE_NAME)
data class DateItemDb(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "date_start") val dateStart: Long,
    @ColumnInfo(name = "date_finish") val dateFinish: Long?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String
) {

    fun toListItemDomain() = DateListItemDomain(id, dateStart, name)

    fun toDomain() =
        DateItem(id, dateStart, dateFinish, name, description)

    companion object {

        const val TABLE_NAME = "dates"

        fun fromDomain(dateDomain: DateItem) = with(dateDomain) {
            DateItemDb(id, dateStart, dateFinish, name, description)
        }
    }
}