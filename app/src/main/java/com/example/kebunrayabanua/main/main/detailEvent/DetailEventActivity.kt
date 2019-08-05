package com.example.kebunrayabanua.main.main.detailEvent

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.util.gone
import com.example.kebunrayabanua.main.util.visible
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.detail_event_activity.*
import org.jetbrains.anko.AnkoLogger

class DetailEventActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener, View.OnClickListener,
    AnkoLogger {

    override fun onClick(v: View?) {
        when (v) {
            backBtn -> finish()
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (verticalOffset <= -55)
            title_bar.visible()
        else
            title_bar.gone()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_event_activity)
        Glide.with(this).load(R.drawable.header_2).into(header_img)
        appbar.addOnOffsetChangedListener(this)
        backBtn.setOnClickListener(this)
    }
}
