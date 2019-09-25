package com.example.kebunrayabanua.main.main.treeData

import com.example.kebunrayabanua.main.api.RetrofitFactory
import com.example.kebunrayabanua.main.api.RetrofitService
import com.example.kebunrayabanua.main.util.CoroutineContextProvider
import com.example.kebunrayabanua.main.util.getFirebaseEmail
import com.example.kebunrayabanua.main.util.isOnline
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import retrofit2.HttpException


class TreeDataPresenter(private val view: TreeDataView,
                        private val service: RetrofitService = RetrofitFactory.makeRetrofitService(),
                        private val context: CoroutineContextProvider = CoroutineContextProvider()) : AnkoLogger {

    fun getItem(pageNumber: Int, find: String) {
        GlobalScope.launch(context.main) {
            view.onLoad()
            if (!isOnline()) {
                view.errorRequest()
            } else {
                val response = service.getTrees(
                        pageNumber,
                        getFirebaseEmail(),
                        find
                )
                try {
                    if (response.isSuccessful) {
                        if (response.body()?.data.isNullOrEmpty())
                            view.closedRequest()
                        else
                            view.showItems(response.body()?.data!!)
                    } else
                        view.errorRequest()
                } catch (e: Throwable) {
                    error { e.message }
                } catch (e: HttpException) {
                    error { e.message }
                }
            }
            view.finishLoad()
        }
    }

}