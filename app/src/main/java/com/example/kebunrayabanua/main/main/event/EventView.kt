package com.example.kebunrayabanua.main.main.event

import com.example.kebunrayabanua.main.model.DataEvent


interface EventView {
    fun showItems(item: List<DataEvent>)
    fun errorRequest()
    fun closedRequest()
    fun onLoad()
    fun finishLoad()
}