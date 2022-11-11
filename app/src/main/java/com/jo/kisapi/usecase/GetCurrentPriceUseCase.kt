package com.jo.kisapi.usecase

import com.jo.kisapi.repository.Repository
import javax.inject.Inject

class GetCurrentPriceUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(FID_INPUT_ISCD: String) = repository.getCurrentPrice(FID_INPUT_ISCD)
}