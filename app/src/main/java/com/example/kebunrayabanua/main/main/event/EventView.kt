package com.example.kebunrayabanua.main.main.event

import com.example.kebunrayabanua.main.model.Highlight


interface EventView {
    fun showItems(item : List<Highlight>)
}