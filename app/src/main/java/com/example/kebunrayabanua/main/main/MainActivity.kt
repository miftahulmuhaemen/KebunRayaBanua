package com.example.kebunrayabanua.main.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.kebunrayabanua.main.login.LoginActivity
import com.example.kebunrayabanua.main.model.Highlight
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.main.scanMe.ScanMeActivity
import com.firebase.ui.auth.AuthUI
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.main_activity.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), MainView {

    override fun highlightItem(item: List<Highlight>) {
        highlightItem.clear()
        highlightItem.addAll(item)
        mAdapter.notifyDataSetChanged()
    }

    override fun headerImages(images: IntArray) {
        for(image in images){
            val imageView = ImageView(this)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            Glide.with(this).load(image).into(imageView)
            viewFlipperMain.addView(imageView)
        }
    }

    private lateinit var mainPresenter : MainPresenter
    private var highlightItem: MutableList<Highlight> = mutableListOf()
    private lateinit var mAdapter: MainListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        PushDownAnim.setPushDownAnimTo(logout, scan, location, database, event, notification)
            .setOnClickListener {
                when(it){
                    logout->{
                        AuthUI.getInstance().signOut(this).addOnCompleteListener {
                            startActivity<LoginActivity>()
                            finish()
                        }
                    }
                    scan->{
                        startActivity<ScanMeActivity>()
                    }
                    location->{

                    }
                    database->{

                    }
                    event->{

                    }
                    notification->{

                    }
                }
            }

        mAdapter = MainListAdapter(this, highlightItem) {
            toast(it.name.toString())
        }
        recylerviewMain.adapter = mAdapter

        mainPresenter = MainPresenter(this)
        mainPresenter.headerImage()
        mainPresenter.highlightItem()

    }
}
