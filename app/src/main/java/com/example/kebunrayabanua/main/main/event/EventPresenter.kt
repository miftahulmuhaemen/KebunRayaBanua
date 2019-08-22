package com.example.kebunrayabanua.main.main.event


import com.example.kebunrayabanua.main.api.RetrofitService
import com.example.kebunrayabanua.main.util.CoroutineContextProvider
import com.example.kebunrayabanua.main.util.isOnline
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import retrofit2.HttpException


class EventPresenter(private val view: EventView,
                     private val service: RetrofitService,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()) : AnkoLogger{

    fun getItem(pageNumber: Int) {
        GlobalScope.launch(context.main) {
            if (!isOnline()) {
                view.errorRequest()
            } else {
                val response = service.getPosts(pageNumber)
                try {
                    if (response.isSuccessful) {
                        if (response.body()!!.isEmpty())
                            view.closedRequest()
                        else
                            view.showItems(response.body()!!)
                    } else {
                        view.errorRequest()
                    }
                } catch (e: HttpException) {
                    info(e.message)
                } catch (e: Throwable) {
                    info(e.message)
                }
            }
        }
    }

}