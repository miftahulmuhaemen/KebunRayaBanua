package com.example.kebunrayabanua.main.main.detailTree

import com.example.kebunrayabanua.main.model.DataTree

interface DetailTreeView {
    fun showItems(item : List<DataTree>)
    fun errorRequest()
    fun closedRequest()
    fun onLoad()
    fun finishLoad()
}