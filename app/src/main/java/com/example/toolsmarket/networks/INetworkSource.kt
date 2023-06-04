package com.example.toolsmarket.networks

import com.example.toolsmarket.models.CardRequest
import retrofit2.Response

interface INetworkSource {
    suspend fun getCards(): Response<List<CardRequest>>
}