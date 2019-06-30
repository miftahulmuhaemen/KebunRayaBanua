package com.example.kebunrayabanua.main.main.profile

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Environment
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.webkit.*
import com.example.kebunrayabanua.R
import kotlinx.android.synthetic.main.profile_activity.*
import kotlinx.android.synthetic.main.profile_app_bar.*
import kotlinx.android.synthetic.main.profile_app_bar.backBtn
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.io.File

class ProfileActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AnkoLogger {

    override fun onClick(view: View?) {
        when(view){
            overflowMenu -> drawer_layout.openDrawer(drawerPosition)
            backBtn -> finish()
        }
    }

    private val drawerPosition = GravityCompat.END

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)

        webview.webViewClient = WebClient()
        webview.settings.javaScriptEnabled = true
        webview.scrollBarSize = View.SCROLLBARS_INSIDE_OVERLAY
        webview.loadUrl("http://simari.ulm.ac.id")

//        webview.settings.loadsImagesAutomatically = true
//        webview.settings.domStorageEnabled = true
//        webview.settings.allowFileAccess = true
//        webview.settings.setAppCachePath(Environment.getDataDirectory().absolutePath + File.separator + "Archive")
//        webview.settings.setAppCacheEnabled(true)
//        if(!isNetworkAvailable())
//            webview.loadUrl(Environment.getDataDirectory().absolutePath + File.separator + "Archive")
//        else

        nav_view.setNavigationItemSelectedListener(this)
        backBtn.setOnClickListener(this)
        overflowMenu.setOnClickListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(drawerPosition))
            drawer_layout.closeDrawer(drawerPosition)
        else
            super.onBackPressed()
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

//    private fun isNetworkAvailable(): Boolean {
//        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        return connectivityManager.activeNetworkInfo?.isConnected ?: false
//    }

}


private class WebClient : WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
    }

    override fun onPageFinished(view: WebView, url: String?) {
        super.onPageFinished(view, url)
//        view.saveWebArchive(  Environment.getDataDirectory().absolutePath + File.separator + "Archive")
    }

    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        super.onReceivedError(view, request, error)
    }

}

