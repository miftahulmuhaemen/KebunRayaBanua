package com.example.kebunrayabanua.main.main.profile

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.kebunrayabanua.R
import kotlinx.android.synthetic.main.profile_activity.*
import kotlinx.android.synthetic.main.profile_app_bar.*
import kotlinx.android.synthetic.main.profile_app_bar.backBtn

class ProfileActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    override fun onClick(v: View?) {
        when(v){
            overflowMenu -> drawer_layout.openDrawer(drawerPosition)
            backBtn -> finish()
        }
    }

    private val drawerPosition = GravityCompat.END

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)
        nav_view.setNavigationItemSelectedListener(this)
        backBtn.setOnClickListener(this)
        overflowMenu.setOnClickListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(drawerPosition)) {
            drawer_layout.closeDrawer(drawerPosition)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.profile -> {

            }
            R.id.visionmission -> {

            }
            R.id.goal -> {

            }
            R.id.structure -> {

            }
            R.id.headprofile -> {

            }
        }

        drawer_layout.closeDrawer(drawerPosition)
        return true
    }
}
