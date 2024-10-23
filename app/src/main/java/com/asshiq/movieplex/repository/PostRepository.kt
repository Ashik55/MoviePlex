package com.asshiq.movieplex.repository

import android.util.Log
import com.asshiq.movieplex.api.ApiService
import com.asshiq.movieplex.api.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class PostRepository @Inject constructor (private val apiService: ApiService) {

    suspend fun getPosts(): Flow<ApiState<List<String>>> = flow {
        Log.d("Repository", "getPosts")
        try {
            emit(ApiState.Loading) // Emit loading state
            val response = apiService.getPost() // Make the network request
            Log.d("Repository", response.toString())
            emit(ApiState.Success(response))
        } catch (e: Exception) {
            emit(ApiState.Error(e.message ?: "Unknown Error")) // Emit error state
        }
    }.flowOn(Dispatchers.IO)
}