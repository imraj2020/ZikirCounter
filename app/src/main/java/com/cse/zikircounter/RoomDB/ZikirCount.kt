package com.cse.zikircounter.RoomDB

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ZikirCount(
    @PrimaryKey
    val name: String,
    val number: Int

)
