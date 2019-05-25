package com.example.kebunrayabanua.main.mainActivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.kebunrayabanua.main.loginActivity.LoginActivity
import com.example.kebunrayabanua.main.model.Highlight
import com.example.kebunrayabanua.R
import com.firebase.ui.auth.AuthUI
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), MainActivityView {

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

    private lateinit var mainPresenter : MainActivityPresenter
    private var highlightItem: MutableList<Highlight> = mutableListOf()
    private lateinit var mAdapter: MainActivityListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        mAdapter = MainActivityListAdapter(this, highlightItem) {
            toast(it.name.toString())
        }
        recylerviewMain.adapter = mAdapter

        mainPresenter = MainActivityPresenter(this)
        mainPresenter.headerImage()
        mainPresenter.highlightItem()

    }
}
