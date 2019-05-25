package com.example.kebunrayabanua.main.mainActivity

import com.example.kebunrayabanua.main.model.Highlight


interface MainActivityView {
    fun headerImages(images : IntArray)
    fun highlightItem(item : List<Highlight>)
}