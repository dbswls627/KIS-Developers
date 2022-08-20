package com.jo.kisapi

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jo.kisapi.dataModel.TokenTime
import kotlinx.coroutines.CoroutineScope

@Database(entities = [TokenTime::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun TokenTimeDao(): TokenTimeDao
    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "TokenTime.db")
                        .fallbackToDestructiveMigration().allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}
    