package com.cse.zikircounter.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ZikirCount::class], version = 1)
abstract class ZikirDatabase : RoomDatabase() {
        abstract fun getZikirDao(): ZikirCountDAO

        companion object {
                @Volatile
                private var INSTANCE: ZikirDatabase? = null

                fun getInstance(context: Context): ZikirDatabase {
                        return INSTANCE ?: synchronized(this) {
                                val instance = Room.databaseBuilder(
                                        context.applicationContext,
                                        ZikirDatabase::class.java,
                                        "Zikir_list.db"
                                )
                                        // Add this line to allow main thread queries (not recommended for production):
                                        .allowMainThreadQueries()
                                        .build()
                                INSTANCE = instance
                                instance
                        }
                }
        }
}
