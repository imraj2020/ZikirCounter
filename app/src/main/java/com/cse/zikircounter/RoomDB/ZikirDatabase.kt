package com.cse.zikircounter.RoomDB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ZikirCount::class], version = 1)
abstract class ZikirDatabase : RoomDatabase() {
        abstract fun getZikirDao():ZikirCountDAO
}