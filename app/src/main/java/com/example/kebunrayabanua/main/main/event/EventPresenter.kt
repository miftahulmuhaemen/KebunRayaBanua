package com.example.kebunrayabanua.main.main.event


import com.example.kebunrayabanua.main.api.ApiRepository
import com.example.kebunrayabanua.main.model.DataEvent
import com.example.kebunrayabanua.main.model.DataEventResponse
import com.example.kebunrayabanua.main.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class EventPresenter(private val view: EventView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()) : AnkoLogger{

    fun getItem(){
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequestAsync(ApiRepository.EventAPI.getEvents("0")).await(),
                    DataEventResponse::class.java)

                info(data.events[0].eventDeskripsi)
//            view.showItems(data.events)

//            val item : MutableList<Highlight> = mutableListOf()
//            val name = context.resources.getStringArray(R.array.highlight_text)
//            val image = context.resources.obtainTypedArray(R.array.highlight_images)
//
//            item.clear()
//            for(j in name.indices)
//                for (i in name.indices) {
//                    item.add(
//                        Highlight(
//                            i.toString(), name[i],
//                            image.getResourceId(i, 0)
//                        )
//                    )
//                }
//            image.recycle()
//            view.showItems(item)
        }
    }

}