package com.jo.kisapi.usecase

import com.jo.kisapi.repository.Repository
import javax.inject.Inject

class GetBalanceUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke() = repository.getInquireBalance()
}