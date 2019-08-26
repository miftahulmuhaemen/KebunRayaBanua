package com.example.kebunrayabanua.main.api

import com.example.kebunrayabanua.BuildConfig.API_KEY
import com.example.kebunrayabanua.main.model.DataDetailTree
import com.example.kebunrayabanua.main.model.DataEvent
import com.example.kebunrayabanua.main.model.DataTree
import com.example.kebunrayabanua.main.model.ResponseTree
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {
    @GET("event/data/all/{pageNumber}/15?key=$API_KEY")
    suspend fun getEvents(
            @Path("pageNumber") pageNumber: Int
    ): Response<List<DataEvent>>

    @FormUrlEncoded
    @POST("tanaman/data/all/{pageNumber}/15")
    suspend fun getTrees(
            @Path("pageNumber") pageNumber: Int,
            @Field("email") email: String,
            @Field("find") find: String,
            @Field("key") key: String = API_KEY
    ): Response<ResponseTree<DataTree>>

    @FormUrlEncoded
    @POST("tanaman/data/{treeNumber}/0/5")
    suspend fun getTree(
            @Path("treeNumber") treeNumber: String,
            @Field("email") email: String,
            @Field("scanme") scanme: Number,
            @Field("key") key: String = API_KEY
    ): Response<ResponseTree<DataDetailTree>>
}