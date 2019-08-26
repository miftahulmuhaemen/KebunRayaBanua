package com.example.kebunrayabanua.main.main.detailTree

import com.example.kebunrayabanua.main.model.DataDetailTree

interface DetailTreeView {
    fun showItems(item: DataDetailTree)
    fun errorRequest()
    fun closedRequest()
    fun onLoad()
    fun finishLoad()
}