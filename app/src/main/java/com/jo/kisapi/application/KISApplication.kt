package com.jo.kisapi.application

import android.app.Application
import com.jo.kisapi.AppDatabase
import com.jo.kisapi.repository.Repository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KISApplication : Application() {

}