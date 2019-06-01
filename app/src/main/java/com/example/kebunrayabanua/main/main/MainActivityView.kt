package com.example.kebunrayabanua.main.main

import com.example.kebunrayabanua.main.model.Highlight


interface MainActivityView {
    fun headerImages(images : IntArray)
    fun highlightItem(item : List<Highlight>)
}