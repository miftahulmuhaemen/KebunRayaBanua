package com.example.kebunrayabanua.main.api

import com.example.kebunrayabanua.BuildConfig.API_KEY
import com.example.kebunrayabanua.main.model.*
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {
    @GET("event/data/all/{pageNumber}/{range}?key=$API_KEY")
    suspend fun getEvents(
            @Path("pageNumber") pageNumber: Int,
            @Path("range") range: Int
    ): Response<List<DataEvent>>

    @FormUrlEncoded
    @POST("tanaman/data/all/{pageNumber}/15")
    suspend fun getTrees(
            @Path("pageNumber") pageNumber: Int,
            @Field("email") email: String?,
            @Field("find") find: String,
            @Field("key") key: String = API_KEY
    ): Response<ResponseTree>

    @FormUrlEncoded
    @POST("tanaman/data/{treeNumber}/0/5")
    suspend fun getTree(
            @Path("treeNumber") treeNumber: String,
            @Field("email") email: String?,
            @Field("scanme") scanme: Number,
            @Field("key") key: String = API_KEY
    ): Response<ResponseDetaiTree>

    @FormUrlEncoded
    @POST("menu/data/all/0/5")
    suspend fun getHeaderHighlight(
        @Field("email") email: String?,
        @Field("firstName") firstName: String?,
        @Field("lastName") lastName: String?,
        @Field("fullName") fullName: String?,
        @Field("displayName") displayName: String?,
        @Field("providerId") providerId: String?,
        @Field("key") key: String = API_KEY
    ): Response<ResponseHeaderHighlight>

}