package com.example.kebunrayabanua.main.main

import androidx.viewpager.widget.ViewPager
import com.example.kebunrayabanua.main.api.RetrofitFactory
import com.example.kebunrayabanua.main.api.RetrofitService
import com.example.kebunrayabanua.main.util.CoroutineContextProvider
import com.example.kebunrayabanua.main.util.isOnline
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.*

class MainPresenter(
    private val view: MainView,
    private val service: RetrofitService = RetrofitFactory.makeRetrofitService(),
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) : AnkoLogger {

    fun getHeaderHighlight() {
        GlobalScope.launch(context.main) {
            if (!isOnline())
            else {
                val email =
                    if (FirebaseAuth.getInstance().currentUser?.providerData?.last()?.email.isNullOrEmpty())
                        ""
                    else
                        FirebaseAuth.getInstance().currentUser?.providerData?.last()?.email
                val response = service.getHeaderHighlight(
                    email, null, null, null,
                    FirebaseAuth.getInstance().currentUser?.displayName,
                    FirebaseAuth.getInstance().currentUser?.providerData?.last()?.providerId
                )
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
        GlobalScope.launch(context.main) {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    if (viewPager.currentItem == imageCount - 1)
                        viewPager.currentItem = 0
                    else
                        viewPager.currentItem++
                }
            }, 3000, 3000)
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