package com.example.kebunrayabanua.main.util

import android.view.View
import com.firebase.ui.auth.AuthUI



object Util {
    const val RC_SIGN_IN: Int = 1
    val GOOGLE_PROVIDER = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
    val TWITTER_PROVIDER = arrayListOf(AuthUI.IdpConfig.TwitterBuilder().build())
}

object Permission {
    const val SCAN = 100
    const val WHEREIAM = 100
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}
