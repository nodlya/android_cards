package com.example.toolsmarket

import android.app.Application
import com.example.toolsmarket.di.AppComponent
import com.example.toolsmarket.di.DaggerAppComponent

class ApplicationCore : Application() {
    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().appContext(this).build()
    }
}