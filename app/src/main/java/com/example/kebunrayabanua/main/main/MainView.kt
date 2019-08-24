package com.example.kebunrayabanua.main.main

import com.example.kebunrayabanua.main.model.DataEvent


interface MainView {
    fun headerImages(images : IntArray)
    fun highlightItem(item: List<DataEvent>)
}