package com.example.toolsmarket.repository

import com.example.toolsmarket.models.CardRequest
import retrofit2.Response
import javax.inject.Inject

class GetAllCardsUseCase  @Inject constructor(
    private val cardRepo: ICardsRepository) : IGetAllCardsUseCase {


    override suspend operator fun invoke(): Response<List<CardRequest>> {
        return cardRepo.getAllCards()
    }
}

interface  IGetAllCardsUseCase {
    suspend operator fun invoke() : Response<List<CardRequest>>
}