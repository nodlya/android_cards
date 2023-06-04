package com.example.toolsmarket.networks

import com.example.toolsmarket.models.CardRequest
import com.example.toolsmarket.repository.RetrofitBuilder
import retrofit2.Response

class ApiNetworkSource: INetworkSource {

    override suspend fun getCards(): Response<List<CardRequest>> {
        return RetrofitBuilder.cardsApi.getCards()
    }

    suspend fun getCardsCycle(): Response<List<CardRequest>> {
        return RetrofitBuilder.cardsApi.getCardsCycle()
    }
}