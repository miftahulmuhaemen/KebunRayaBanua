package com.example.kebunrayabanua.main.main.event

import com.example.kebunrayabanua.main.model.DataEvent


interface EventView {
    fun addItems(item: List<DataEvent>)
    fun initialItems(item: List<DataEvent>)
    fun closedRequest()
}