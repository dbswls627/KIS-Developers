package com.jo.kisapi

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface TokenTimeDao {
 @Query("select time from TokenTime")
 fun getTime(): String

 @Query("select token from TokenTime")
 fun getToken(): String

 @Insert(onConflict = REPLACE)
 fun insert(tokenTime: TokenTime)

 @Query("insert into TokenTime(type,token,time) values(:type, :token, :time)")
 fun insert(type:String,token: String, time :String)

 @Delete
 fun delete(tokenTime: TokenTime)

 @Update
 fun upadte(tokenTime: TokenTime)
}
    