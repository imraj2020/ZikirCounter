package com.cse.zikircounter.RoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ZikirCount(


  //  val id:Int=1,
    @PrimaryKey
    val name : String,
    val number : Int

)
