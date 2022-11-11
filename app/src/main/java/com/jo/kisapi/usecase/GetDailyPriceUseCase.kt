package com.jo.kisapi.usecase

import com.jo.kisapi.repository.Repository
import javax.inject.Inject

class GetDailyPriceUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(FID_INPUT_ISCD: String) = repository.getDailyPrice(FID_INPUT_ISCD)

}