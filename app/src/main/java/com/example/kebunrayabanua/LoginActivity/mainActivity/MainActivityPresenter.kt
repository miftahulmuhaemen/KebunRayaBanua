package com.example.kebunrayabanua.LoginActivity.mainActivity

import android.app.Activity
import com.example.kebunrayabanua.LoginActivity.model.hightlight
import com.example.kebunrayabanua.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivityPresenter(private val activity : MainActivity) {

        fun headerImage(){
            GlobalScope.launch(Dispatchers.Main){
                val images = intArrayOf(R.drawable.header_1, R.drawable.header_2, R.drawable.header_3)
                activity.headerImages(images)
            }
        }

        fun highlightItem(){
            GlobalScope.launch(Dispatchers.Main){
                val item : MutableList<hightlight> = mutableListOf()
                val name = activity.resources.getStringArray(R.array.highlight_text)
                val image = activity.resources.obtainTypedArray(R.array.highlight_images)

                item.clear()
                for (i in name.indices) {
                    item.add(
                        hightlight(
                            i.toString(), name[i],
                            image.getResourceId(i, 0)
                        )
                    )
                }
                image.recycle()
                activity.highlightItem(item)
            }
        }

}