package com.example.kebunrayabanua.main.main.detailTree

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.transition.Visibility
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.kebunrayabanua.R
import kotlinx.android.synthetic.main.detail_tree_activity.*
import kotlinx.android.synthetic.main.detail_tree_activity.appbar
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.dip
import org.jetbrains.anko.toast
import org.jetbrains.anko.verbose

class DetailTreeActivity : AppCompatActivity(), AnkoLogger, AppBarLayout.OnOffsetChangedListener {

    override fun onOffsetChanged(p0: AppBarLayout?, verticalOffset: Int) {
        if(verticalOffset <= -55)
            title_bar.visibility = View.VISIBLE
        else
            title_bar.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_tree_activity)
        Glide.with(this).load(R.drawable.header_2).into(header_img)
        appbar.addOnOffsetChangedListener(this)
    }

}
