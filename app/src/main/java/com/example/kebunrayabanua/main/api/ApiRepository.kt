package com.example.kebunrayabanua.main.api

import com.example.kebunrayabanua.BuildConfig
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.net.URL

class ApiRepository {
    object EventAPI {
        fun getEvents(pageNumber: String, rowNumber: String = "15"): String {
            return BuildConfig.BASE_URL + "event/data/all/" + "$pageNumber/$rowNumber?" + "key=" + BuildConfig.API_KEY
        }
    }

    fun doRequestAsync(url: String): Deferred<String> = GlobalScope.async {
        URL(url).readText()
    }
}