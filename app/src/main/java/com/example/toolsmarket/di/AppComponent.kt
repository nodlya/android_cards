package com.example.toolsmarket.di

import android.app.Application
import android.content.Context
import com.example.toolsmarket.CardFragment
import com.example.toolsmarket.MainActivity
import com.example.toolsmarket.repository.CardsApi
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}

@Module
class NetworkModule(private val application: Application) {

    @Provides
    fun getApplication(): Application = application

    @Provides
    fun getContext(): Context = application.baseContext

    @Provides
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://develtop.ru/study/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun getApi(retrofit: Retrofit): CardsApi =
        retrofit.create(CardsApi::class.java)
}