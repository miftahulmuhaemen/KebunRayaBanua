package com.example.kebunrayabanua.main.main

import com.example.kebunrayabanua.main.model.Highlight
import com.example.kebunrayabanua.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainPresenter(private val activity : MainActivity) {

        fun headerImage(){
            GlobalScope.launch(Dispatchers.Main){
                val images = intArrayOf(R.drawable.header_0, R.drawable.header_3)
                activity.headerImages(images)
            }
        }

        fun highlightItem(){
            GlobalScope.launch(Dispatchers.Main){
                val item : MutableList<Highlight> = mutableListOf()
                val name = activity.resources.getStringArray(R.array.highlight_text)
                val image = activity.resources.obtainTypedArray(R.array.highlight_images)

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
                activity.highlightItem(item)
            }
        }

}