package com.example.toolsmarket.repository

import com.example.toolsmarket.models.CardRequest
import retrofit2.Response
import javax.inject.Inject

class CardsRepository @Inject constructor(
    private val cardApiRepo: CardsApi
) : ICardsRepository{


    override suspend fun getAllCards(): Response<List<CardRequest>> {
        return cardApiRepo.getCards();
    }
}

interface ICardsRepository {
    suspend fun getAllCards() : Response<List<CardRequest>>
}