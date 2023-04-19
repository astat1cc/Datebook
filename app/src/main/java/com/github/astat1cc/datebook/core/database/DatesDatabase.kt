package com.github.astat1cc.datebook.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.astat1cc.datebook.datelist.data.local.dao.DateListDao
import com.github.astat1cc.datebook.core.database.model.DateItemDb

@Database(
    entities = [DateItemDb::class],
    version = 1
)
abstract class DatesDatabase : RoomDatabase() {

    abstract fun dateListDao(): DateListDao

    companion object {

        const val DATABASE_NAME = "dates_database"
    }
}