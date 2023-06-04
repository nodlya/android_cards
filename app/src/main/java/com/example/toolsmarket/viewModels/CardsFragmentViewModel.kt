package com.example.toolsmarket.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.toolsmarket.models.Card
import com.example.toolsmarket.networks.ApiNetworkSource
import com.example.toolsmarket.networks.INetworkSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.lifecycle.viewModelScope
import com.example.toolsmarket.models.LoadingResult
import com.example.toolsmarket.repository.GetAllCardsUseCase
import com.example.toolsmarket.repository.IGetAllCardsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CardsFragmentViewModel  @Inject constructor(
    private val useCase: IGetAllCardsUseCase
) : ViewModel() {

    val liveData = MutableLiveData<LoadingResult<List<Card>?>>()

    fun init() {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                useCase()
            }
            if (response.isSuccessful) {
                val responses = response.body()
                val cards = responses?.let { Card.getCards(it) }
                val result = LoadingResult.Success(cards)
                liveData.postValue(result)
            } else {
                liveData.postValue(LoadingResult.Failure("internet error", "check your internet connection"))
            }
        }
    }
}