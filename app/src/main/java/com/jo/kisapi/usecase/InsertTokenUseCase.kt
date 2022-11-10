package com.jo.kisapi.usecase

import com.jo.kisapi.dataModel.TokenTime
import com.jo.kisapi.repository.Repository
import javax.inject.Inject

class InsertTokenUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(token: TokenTime) = repository.insert(token)
}