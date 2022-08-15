package com.jo.kisapi.application

import android.app.Application
import com.jo.kisapi.AppDatabase
import com.jo.kisapi.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class KISApplication : Application() {
    private val database by lazy { AppDatabase.getInstance(this) }
    val repository by lazy { Repository(database!!.TokenTimeDao()) }
}