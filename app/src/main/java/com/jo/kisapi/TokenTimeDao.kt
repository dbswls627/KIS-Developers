package com.jo.kisapi

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface TokenTimeDao {
 @Query("select * from TokenTime")
 fun getLiveData(): LiveData<List<TokenTime>>

 @Insert(onConflict = REPLACE)
 fun insert(tokenTime: TokenTime)

 @Query("insert into TokenTime(type,token,time) values(:type, :token, :time)")
 fun insert(type:String,token: String, time :String)

 @Delete
 fun delete(tokenTime: TokenTime)

 @Update
 fun upadte(tokenTime: TokenTime)
}
    