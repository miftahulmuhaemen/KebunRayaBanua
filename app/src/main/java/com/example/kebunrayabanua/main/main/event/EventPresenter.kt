package com.example.kebunrayabanua.main.main.event


import com.example.kebunrayabanua.main.api.ApiRepository
import com.example.kebunrayabanua.main.model.DataEvent
import com.example.kebunrayabanua.main.util.CoroutineContextProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger


class EventPresenter(private val view: EventView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()) : AnkoLogger{

    fun getItem(pageNumber: Int) {
        GlobalScope.launch(context.main) {
            val data = gson.fromJson<List<DataEvent>>(apiRepository.doRequestAsync(ApiRepository.EventAPI.getEvents(pageNumber)).await(),
                    object : TypeToken<List<DataEvent>>(){}.type)
            when {
                data.isEmpty() -> view.closedRequest()
                pageNumber == 0 -> view.initialItems(data)
                else -> view.addItems(data)
            }
        }
    }

}