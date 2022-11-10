package com.jo.kisapi.usecase

import com.jo.kisapi.repository.Repository
import javax.inject.Inject

class GetBuySumUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(type:String) = repository.getBuySum(type)
}