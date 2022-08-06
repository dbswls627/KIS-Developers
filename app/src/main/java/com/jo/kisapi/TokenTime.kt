package com.jo.kisapi

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
    data class TokenTime(
        @PrimaryKey
        var type:String,
        var token:String,
        var time:String
)
    