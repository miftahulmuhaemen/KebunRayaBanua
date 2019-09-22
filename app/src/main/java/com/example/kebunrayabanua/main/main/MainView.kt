package com.example.kebunrayabanua.main.main

import com.example.kebunrayabanua.main.model.DataEvent


interface MainView {
    fun headerImages(images : List<String>)
    fun highlightItem(item: List<DataEvent>)
}