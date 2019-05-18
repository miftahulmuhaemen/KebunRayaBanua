package com.example.kebunrayabanua.LoginActivity.mainActivity

import com.example.kebunrayabanua.LoginActivity.model.hightlight


interface MainActivityView {
    fun headerImages(images : IntArray)
    fun highlightItem(item : List<hightlight>)
}