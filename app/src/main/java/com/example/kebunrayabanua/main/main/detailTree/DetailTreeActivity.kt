package com.example.kebunrayabanua.main.main.detailTree

import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.util.gone
import com.example.kebunrayabanua.main.util.visible
import kotlinx.android.synthetic.main.detail_tree_activity.*
import org.jetbrains.anko.AnkoLogger

class DetailTreeActivity : AppCompatActivity(), AnkoLogger, AppBarLayout.OnOffsetChangedListener, View.OnClickListener {

    override fun onClick(v: View?) {
        when (v) {
            backBtn -> finish()
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if(verticalOffset <= -55)
            title_bar.visible()
        else
            title_bar.gone()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_tree_activity)
        Glide.with(this).load(R.drawable.header_2).into(header_img)
        appbar.addOnOffsetChangedListener(this)
        backBtn.setOnClickListener(this)
    }

}
