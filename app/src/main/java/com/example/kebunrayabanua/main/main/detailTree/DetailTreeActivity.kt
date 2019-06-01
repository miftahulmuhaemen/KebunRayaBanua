package com.example.kebunrayabanua.main.main.detailTree

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.kebunrayabanua.R
import kotlinx.android.synthetic.main.activity_detail_tree.*

class DetailTreeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tree)
        setSupportActionBar(toolbar)
        collapsing_toolbar.title = "Eucalypus Aselole"
    }

}
