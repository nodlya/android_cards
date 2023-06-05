package com.example.toolsmarket.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.toolsmarket.CardFragment
import com.example.toolsmarket.MainActivity
import com.example.toolsmarket.repository.*
import com.example.toolsmarket.viewModels.CardsFragmentViewModel
import com.example.toolsmarket.viewModels.CardsFragmentViewModelFactory
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Provider

@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: CardFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(context: Context): Builder
        fun build(): AppComponent
    }
}

@Module
abstract class NetworkModule() {

    companion object {

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

        @Provides
        fun provideMyViewModelFactory(myViewModelProvider: Provider<CardsFragmentViewModel>): CardsFragmentViewModelFactory {
            return CardsFragmentViewModelFactory(myViewModelProvider)
        }

        @Provides
        fun provideMyViewModel(myViewModelFactory: CardsFragmentViewModelFactory): ViewModelProvider.Factory {
            return myViewModelFactory
        }
    }


    @Binds
    abstract fun getRepository(repository: CardsRepository) : ICardsRepository

    @Binds
    abstract fun getUseCase(useCase: GetAllCardsUseCase) : IGetAllCardsUseCase


}