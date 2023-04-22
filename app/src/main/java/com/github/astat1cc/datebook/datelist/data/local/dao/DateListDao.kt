package com.github.astat1cc.datebook.datelist.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.astat1cc.datebook.core.database.model.DateItemDb
import kotlinx.coroutines.flow.Flow

@Dao
interface DateListDao {

    @Query(
        "SELECT * FROM ${DateItemDb.TABLE_NAME} " +
                "WHERE date_start >= :dateTimestampFrom and date_start < :dateTimestampTo"
    )
    fun fetchDateList(dateTimestampFrom: Long, dateTimestampTo: Long): Flow<List<DateItemDb>>

    @Insert(entity = DateItemDb::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun saveDate(date: DateItemDb)
}