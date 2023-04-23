package com.github.astat1cc.datebook.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.astat1cc.datebook.datelist.data.local.dao.DateListDao
import com.github.astat1cc.datebook.core.database.model.DateItemDb
import com.github.astat1cc.datebook.datedetails.data.local.DateDetailsDao

@Database(
    entities = [DateItemDb::class],
    version = 1
)
abstract class DatesDatabase : RoomDatabase() {

    abstract fun dateListDao(): DateListDao

    abstract fun dateDetailsDao(): DateDetailsDao

    companion object {

        const val DATABASE_NAME = "dates_database"
    }
}