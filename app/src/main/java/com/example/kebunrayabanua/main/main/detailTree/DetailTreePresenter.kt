package com.example.kebunrayabanua.main.main.detailTree

import com.example.kebunrayabanua.main.api.RetrofitFactory
import com.example.kebunrayabanua.main.api.RetrofitService
import com.example.kebunrayabanua.main.main.treeData.TreeDataView
import com.example.kebunrayabanua.main.util.CoroutineContextProvider
import com.example.kebunrayabanua.main.util.isOnline
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import retrofit2.HttpException

class DetailTreePresenter (private val view: DetailTreeView,
                           private val service: RetrofitService = RetrofitFactory.makeRetrofitService(),
                           private val context: CoroutineContextProvider = CoroutineContextProvider()) : AnkoLogger {

    fun getItem(treeNumber: String) {
        GlobalScope.launch(context.main) {
            view.onLoad()
            if (!isOnline()) {
                view.errorRequest()
            } else {
                val response = service.getTree(
                        treeNumber,
                        FirebaseAuth.getInstance().currentUser?.email.toString()
                )
                try {
                    if (response.isSuccessful) {
                        if (response.body()?.data?.isEmpty()!!)
                            view.closedRequest()
                        else
                            view.showItems(response.body()?.data!!)
                    } else
                        view.errorRequest()
                } catch (e: Throwable) {
                    info { e.message }
                } catch (e: HttpException) {
                    info { e.message }
                }
            }
            view.finishLoad()
        }
    }

}