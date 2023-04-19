package com.github.astat1cc.datebook.datelist.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.astat1cc.datebook.core.database.model.DateItemDb
import kotlinx.coroutines.flow.Flow

@Dao
interface DateListDao {

    @Query("SELECT * FROM ${DateItemDb.TABLE_NAME}")
    fun fetchDateList(): Flow<List<DateItemDb>>

    @Insert
    suspend fun saveDateList(dateList: List<DateItemDb>)
}