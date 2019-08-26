package com.example.kebunrayabanua.main.main.treeData

import com.example.kebunrayabanua.main.model.DataTree

interface TreeDataView {
    fun showItems(item : List<DataTree>)
    fun errorRequest()
    fun closedRequest()
    fun onLoad()
    fun finishLoad()
}