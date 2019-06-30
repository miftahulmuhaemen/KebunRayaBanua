package com.example.kebunrayabanua.main.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.login.LoginActivity
import com.example.kebunrayabanua.main.main.profile.ProfileActivity
import com.example.kebunrayabanua.main.main.scanMe.ScanMeActivity
import com.example.kebunrayabanua.main.main.treeData.TreeDataActivity
import com.example.kebunrayabanua.main.main.whereIam.WhereIamActivity
import com.example.kebunrayabanua.main.model.Highlight
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.main_activity.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

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
            event -> {}
        }
    }

    override fun highlightItem(item: List<Highlight>) {
        highlightItem.clear()
        highlightItem.addAll(item)
        mAdapter.notifyDataSetChanged()
    }

    override fun headerImages(images: IntArray) {
        for (image in images) {
            val imageView = ImageView(this)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load(image).into(imageView)
            viewFlipperMain.addView(imageView)
        }
    }

    private lateinit var mainPresenter: MainPresenter
    private var highlightItem: MutableList<Highlight> = mutableListOf()
    private lateinit var mAdapter: MainListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        mAdapter = MainListAdapter(this, highlightItem) {
            toast(it.name.toString())
        }
        recylerviewMain.adapter = mAdapter

        mainPresenter = MainPresenter(this, this)
        mainPresenter.headerImage()
        mainPresenter.highlightItem()

        logout.setOnClickListener(this)
        scan.setOnClickListener(this)
        location.setOnClickListener(this)
        database.setOnClickListener(this)
        event.setOnClickListener(this)
        profile.setOnClickListener(this)
    }
}
