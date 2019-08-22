package com.example.kebunrayabanua.main.main

import android.content.Context
import androidx.viewpager.widget.ViewPager
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.model.Highlight
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.*

class MainPresenter(private val context: Context, private val view: MainView) : AnkoLogger {

    fun headerImage() {
        GlobalScope.launch(Dispatchers.Main) {
            val images = intArrayOf(R.drawable.header_0, R.drawable.header_3)
            view.headerImages(images)
        }
    }

    fun highlightItem() {
        GlobalScope.launch(Dispatchers.Main) {
            val item: MutableList<Highlight> = mutableListOf()
            val name = context.resources.getStringArray(R.array.highlight_text)
            val image = context.resources.obtainTypedArray(R.array.highlight_images)

            item.clear()
            for (i in name.indices) {
                item.add(
                    Highlight(
                        i.toString(), name[i],
                        image.getResourceId(i, 0)
                    )
                )
            }
            image.recycle()
            view.highlightItem(item)
        }
    }

    fun viewPagerAutoScroll(imageCount: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    val viewPager = ViewPager(context)
                    if (viewPager.currentItem == imageCount - 1)
                        viewPager.currentItem = 0
                    else
                        viewPager.currentItem++
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