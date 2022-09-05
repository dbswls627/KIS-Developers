package com.jo.kisapi.dataModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TokenTime(
    @PrimaryKey
    var type:String,
    var token:String,
    var time:String
)
@Entity
data class AutoTrading(
    var type:String,
    var division:String,
    @PrimaryKey
    var odno:String
)
