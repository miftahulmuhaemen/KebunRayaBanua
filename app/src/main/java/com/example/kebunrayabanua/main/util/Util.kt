package com.example.kebunrayabanua.main.util

import com.firebase.ui.auth.AuthUI

object Util {
    const val RC_SIGN_IN: Int = 1
    val GOOGLE_PROVIDER = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
    val TWITTER_PROVIDER = arrayListOf(AuthUI.IdpConfig.TwitterBuilder().build())
}