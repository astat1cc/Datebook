package com.github.astat1cc.datebook.datedetails.data.local

import androidx.room.Dao
import androidx.room.Query
import com.github.astat1cc.datebook.core.database.model.DateItemDb

@Dao
interface DateDetailsDao {

    @Query("SELECT * FROM ${DateItemDb.TABLE_NAME} WHERE id = :id")
    suspend fun fetchDate(id: Int): DateItemDb
}