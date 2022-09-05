package com.jo.kisapi

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.jo.kisapi.dataModel.AutoTrading
import com.jo.kisapi.dataModel.TokenTime

@Dao
interface TokenTimeDao {
 @Query("select time from TokenTime")
 suspend fun getTime(): String

 @Query("select token from TokenTime")
 suspend fun getToken(): String

 @Insert(onConflict = REPLACE)
 suspend fun insert(tokenTime: TokenTime)

 @Delete
 suspend fun delete(tokenTime: TokenTime)

 @Update
 suspend fun upadte(tokenTime: TokenTime)

 @Insert(onConflict = REPLACE)
 suspend fun insertOrderHistory(autoTrading: AutoTrading)
}
    