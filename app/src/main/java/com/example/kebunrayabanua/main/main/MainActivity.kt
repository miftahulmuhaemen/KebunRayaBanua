package com.example.kebunrayabanua.main.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.login.LoginActivity
import com.example.kebunrayabanua.main.main.detailEvent.DetailEventActivity
import com.example.kebunrayabanua.main.main.event.EventActivity
import com.example.kebunrayabanua.main.main.profile.ProfileActivity
import com.example.kebunrayabanua.main.main.scanMe.ScanMeActivity
import com.example.kebunrayabanua.main.main.treeData.TreeDataActivity
import com.example.kebunrayabanua.main.main.whereIam.WhereIamActivity
import com.example.kebunrayabanua.main.model.DataEvent
import com.example.kebunrayabanua.main.util.ViewpagerAdapter
import com.example.kebunrayabanua.main.util.ViewpagerAdapter.Type.HEADER
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.main_activity.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(), MainView, View.OnClickListener {

    override fun onClick(v: View?) {
        when (v) {
            logout -> {
                AuthUI.getInstance().signOut(this).addOnCompleteListener {
                    startActivity<LoginActivity>()
                    finish()
                }
            }
            scan -> startActivity<ScanMeActivity>()
            location -> startActivity<WhereIamActivity>()
            database -> startActivity<TreeDataActivity>()
            profile -> startActivity<ProfileActivity>()
            event -> startActivity<EventActivity>()
        }
    }

    override fun highlightItem(item: List<DataEvent>) {
        mainItem.clear()
        mainItem.addAll(item)
        mAdapter.notifyDataSetChanged()
    }

    override fun headerImages(images: List<String>) {
        Glide.with(this).load(R.drawable.header_0).into(stockImage)
        viewPagerMain.adapter = ViewpagerAdapter(HEADER, this, images)
        mainPresenter.viewPagerAutoScroll(images.count(), viewPagerMain)
    }

    private lateinit var mainPresenter: MainPresenter
    private var mainItem: MutableList<DataEvent> = mutableListOf()
    private lateinit var mAdapter: MainListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        mAdapter = MainListAdapter(this, mainItem) {
            startActivity<DetailEventActivity>(DetailEventActivity.DETAIL_EVENT to it)
        }
        recylerviewMain.adapter = mAdapter
        mainPresenter = MainPresenter(this)
        mainPresenter.getHeaderHighlight()
        mainPresenter.firebaseSubscribeTopic()

        logout.setOnClickListener(this)
        scan.setOnClickListener(this)
        location.setOnClickListener(this)
        database.setOnClickListener(this)
        event.setOnClickListener(this)
        profile.setOnClickListener(this)
    }
}
