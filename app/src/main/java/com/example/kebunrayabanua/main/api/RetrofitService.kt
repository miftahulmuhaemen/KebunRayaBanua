package com.example.kebunrayabanua.main.api

import com.example.kebunrayabanua.BuildConfig.API_KEY
import com.example.kebunrayabanua.main.model.DataEvent
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {
    @GET("event/data/all/{pageNumber}/5?key=$API_KEY")
    suspend fun getPosts(@Path("pageNumber") pageNumber: Int): Response<List<DataEvent>>
}