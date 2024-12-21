package com.example.fetchtakehomeproject.retrofit

import com.example.fetchtakehomeproject.models.FetchItem
import retrofit2.Response
import retrofit2.http.GET

interface FetchApi {
    @GET("/hiring")
    suspend fun getFetchItems(): Response<List<FetchItem>>
}