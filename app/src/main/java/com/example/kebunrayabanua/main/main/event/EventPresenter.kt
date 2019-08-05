package com.example.kebunrayabanua.main.main.event


import android.content.Context
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.main.treeData.TreeDataView
import com.example.kebunrayabanua.main.model.Highlight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class EventPresenter(private val context: Context, private val view: EventView) {

    fun getItem(){
        GlobalScope.launch(Dispatchers.Main){
            val item : MutableList<Highlight> = mutableListOf()
            val name = context.resources.getStringArray(R.array.highlight_text)
            val image = context.resources.obtainTypedArray(R.array.highlight_images)

            item.clear()
            for(j in name.indices)
                for (i in name.indices) {
                    item.add(
                        Highlight(
                            i.toString(), name[i],
                            image.getResourceId(i, 0)
                        )
                    )
                }
            image.recycle()
            view.showItems(item)
        }
    }

}