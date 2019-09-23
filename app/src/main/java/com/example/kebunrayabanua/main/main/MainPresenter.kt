package com.example.kebunrayabanua.main.main

import androidx.viewpager.widget.ViewPager
import com.example.kebunrayabanua.main.api.RetrofitFactory
import com.example.kebunrayabanua.main.api.RetrofitService
import com.example.kebunrayabanua.main.util.*
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.*
import kotlin.concurrent.schedule

class MainPresenter(
    private val view: MainView,
    private val service: RetrofitService = RetrofitFactory.makeRetrofitService(),
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : AnkoLogger {

    fun getHeaderHighlight() {
        GlobalScope.launch(context.main) {
            if (!isOnline())
            else {
                val response = service.getHeaderHighlight(
                    getFirebaseEmail(), null, null, null,
                    getFirebaseDisplayName(),
                    getFirebaseProviderId())

                try {
                    if (response.isSuccessful)
                        response.body()?.let {
                            it.galeri?.let { it1 -> view.headerImages(it1) }
                            it.event?.let { it1 -> view.highlightItem(it1) }
                        }
                    else {

                    }
                } catch (e: Throwable) {
                    info(e.message)
                }
            }
        }
    }

    fun viewPagerAutoScroll(imageCount: Int, viewPager: ViewPager) {
        Timer("autoScroll", false).schedule(3000, 3000) {
            GlobalScope.launch(context.main) {
                    if (viewPager.currentItem == imageCount - 1)
                        viewPager.currentItem = 0
                    else
                        viewPager.currentItem++
                }
        }
    }

    fun firebaseSubscribeTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("event")
            .addOnCompleteListener { task ->
                var msg = "Success"
                if (!task.isSuccessful)
                    msg = "Not Success"
                info(msg)
            }
    }
}