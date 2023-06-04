package com.example.toolsmarket.repository

import com.example.toolsmarket.models.CardRequest
import retrofit2.Response
import retrofit2.http.GET

interface CardsApi {
    @GET("new_text.json")
    suspend fun getCards(): Response<List<CardRequest>>


    //то же самое через lifecyclescope
    @GET("new_text.json")
    suspend fun getCardsCycle(): Response<List<CardRequest>>
}