package com.jo.kisapi.usecase

import com.jo.kisapi.repository.Repository
import javax.inject.Inject

class GetTradingDBHistoryUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(type:String,division:String) = repository.getTradingHistory(type,division)

}