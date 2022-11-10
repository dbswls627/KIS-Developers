package com.jo.kisapi.usecase

import com.jo.kisapi.dataModel.AutoTrading
import com.jo.kisapi.repository.Repository
import javax.inject.Inject

class InsertTradingHistoryUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(autoTrading: AutoTrading) = repository.insert(autoTrading)
}