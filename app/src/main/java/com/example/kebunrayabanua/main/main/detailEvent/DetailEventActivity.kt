package com.example.kebunrayabanua.main.main.detailEvent

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.model.DataEvent
import com.example.kebunrayabanua.main.util.DetailViewpagerAdapter
import com.example.kebunrayabanua.main.util.DetailViewpagerAdapter.Type.DEFAULT
import com.example.kebunrayabanua.main.util.getHtml
import com.example.kebunrayabanua.main.util.gone
import com.example.kebunrayabanua.main.util.visible
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.detail_event_activity.*
import org.jetbrains.anko.AnkoLogger

class DetailEventActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener, View.OnClickListener,
        AnkoLogger {

    companion object {
        const val DETAIL_EVENT = "DETAIL_EVENT"
    }

    override fun onClick(v: View?) {
        when (v) {
            backBtn -> finish()
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (verticalOffset <= -55)
            item_title.visible()
        else
            item_title.gone()
    }

    private lateinit var data: DataEvent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_event_activity)

        data = intent.getParcelableExtra(DETAIL_EVENT)
        item_title.text = data.eventNama
        item_duration.text = "${data.eventMulai} - ${data.eventSelesai}"
        item_desc.text = getHtml(data.eventDeskripsi.toString())
        viewPagerEvent.adapter = data.eventPoster?.let { DetailViewpagerAdapter(DEFAULT, this, it) }


        appbar.addOnOffsetChangedListener(this)
        backBtn.setOnClickListener(this)
    }


}
