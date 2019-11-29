package com.example.kebunrayabanua.main.main.profile

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.kebunrayabanua.BuildConfig
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.util.gone
import com.example.kebunrayabanua.main.util.visible
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.profile_activity.*
import kotlinx.android.synthetic.main.profile_app_bar.*
import org.jetbrains.anko.AnkoLogger

class ProfileActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
    AnkoLogger {

    companion object {
        const val EXTENDED_URL = "${BuildConfig.BASE_URL}index.php/profil/organisasi/"
    }

    override fun onClick(view: View?) {
        when (view) {
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
        webview.settings.loadsImagesAutomatically = true
        webview.settings.domStorageEnabled = true
        webview.settings.allowFileAccess = true
        webview.settings.setAppCacheEnabled(true)
        webview.scrollBarSize = View.SCROLLBARS_INSIDE_OVERLAY
        webview.loadUrl("${EXTENDED_URL}profil")
    }

    override fun onStart() {
        super.onStart()
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
                webview.loadUrl("${EXTENDED_URL}profil")
            }
            R.id.visionmission -> {
                webview.loadUrl("${EXTENDED_URL}visimisi")
            }
            R.id.goal -> {
                webview.loadUrl("${EXTENDED_URL}tujuan")
            }
            R.id.structure -> {
                webview.loadUrl("${EXTENDED_URL}struktur")
            }
            R.id.headprofile -> {
                webview.loadUrl("${EXTENDED_URL}pimpinan")
            }
        }
        drawer_layout.closeDrawer(drawerPosition)
        return true
    }

    inner class WebClient : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progress_circular.visible()
        }

        override fun onPageFinished(view: WebView, url: String?) {
            super.onPageFinished(view, url)
            progress_circular.gone()
        }

    }
}


