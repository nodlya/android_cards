package com.example.toolsmarket

import android.app.Application
import com.example.toolsmarket.di.AppComponent

class ApplicationCore : Application() {
    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        //component = DaggerAppComponent.builder().appContext(this).build()
    }
}