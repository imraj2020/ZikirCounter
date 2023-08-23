package com.cse.zikircounter.RoomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ZikirCountDAO {


    //CRUD
    @Insert
    fun InsertCount(zikirCount: ZikirCount)

    @Update
    fun UpdateCount(zikirCount: ZikirCount)

    @Delete
    fun DeleteCount(zikirCount: ZikirCount)


    //Read
    @Query("SELECT * FROM ZikirCount")
    fun AllCount(): List<ZikirCount>




}