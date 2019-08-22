package com.example.kebunrayabanua.main.api

import com.example.kebunrayabanua.BuildConfig.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    fun makeRetrofitService(): RetrofitService {
        return Retrofit.Builder()
                .baseUrl(BASE_URL + "index.php/api/")
                .client(makeOkHttpClient())
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(RetrofitService::class.java)
    }

    private fun makeOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(makeLoggingInterceptor())
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .build()
    }

    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level =
                HttpLoggingInterceptor.Level.BODY
        return logging
    }


}
