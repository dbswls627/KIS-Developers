package com.jo.kisapi.usecase

import com.jo.kisapi.repository.Repository
import javax.inject.Inject

class GetTradingHistoryUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(startDay: String, endDay: String, division: String) =
        repository.getTradingHistory(startDay, endDay, division)
}