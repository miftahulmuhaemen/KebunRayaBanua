package com.example.kebunrayabanua.main.main

import android.content.Context
import androidx.viewpager.widget.ViewPager
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.api.RetrofitFactory
import com.example.kebunrayabanua.main.api.RetrofitService
import com.example.kebunrayabanua.main.util.CoroutineContextProvider
import com.example.kebunrayabanua.main.util.isOnline
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.*

class MainPresenter(private val view: MainView,
                    private val service: RetrofitService = RetrofitFactory.makeRetrofitService(),
                    private val context: CoroutineContextProvider = CoroutineContextProvider()) : AnkoLogger {

    fun headerImage() {
        GlobalScope.launch(context.main) {
            val images = intArrayOf(R.drawable.header_0, R.drawable.header_3)
            view.headerImages(images)
        }
    }

    fun highlightItem() {
        GlobalScope.launch(context.main) {
            if (!isOnline())

            else {
                val response = service.getEvents(0, 5)
                try {
                    if (response.isSuccessful)
                        response.body()?.let { view.highlightItem(it) }
                    else
                    {}
                } catch (e: Throwable) {
                    info(e.message)
                }
            }
        }
    }

    fun viewPagerAutoScroll(imageCount: Int, viewPager: ViewPager) {
        GlobalScope.launch(context.main) {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    if (viewPager.currentItem == imageCount - 1)
                        viewPager.currentItem = 0
                    else
                        viewPager.currentItem ++
                }
            }, 3000, 3000)
        }
    }

    fun firebaseSubscribeTopic(){
        FirebaseMessaging.getInstance().subscribeToTopic("event")
            .addOnCompleteListener { task ->
                var msg = "Success"
                if (!task.isSuccessful)
                    msg = "Not Success"
                info(msg)
            }
    }
}