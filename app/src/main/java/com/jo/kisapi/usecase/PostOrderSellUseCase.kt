package com.jo.kisapi.usecase

import com.jo.kisapi.repository.Repository
import javax.inject.Inject

class PostOrderSellUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(PDNO: String, division: String, count: String, amt: String) =
        repository.orderBuy(PDNO, division, count, amt)
}