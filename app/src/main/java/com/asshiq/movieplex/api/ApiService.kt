package com.asshiq.movieplex.api

import com.asshiq.movieplex.data.Post
import retrofit2.http.GET

interface ApiService {

    companion object{
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }

    @GET("posts")
    suspend fun getPost():List<Post>

}