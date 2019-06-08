package com.example.kebunrayabanua.main.main.detailTree

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.kebunrayabanua.R
import kotlinx.android.synthetic.main.detail_tree_activity.*

class DetailTreeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_tree_activity)
        setSupportActionBar(toolbar)
        collapsing_toolbar.title = "Eucalypus Aselole"
    }

}
