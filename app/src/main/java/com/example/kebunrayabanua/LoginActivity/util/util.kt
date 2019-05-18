package com.example.kebunrayabanua.LoginActivity.util

import com.firebase.ui.auth.AuthUI
import java.util.*

object util {
    val USERNAME = "username"
    val RC_SIGN_IN: Int = 1
    val GOOGLE_PROVIDER = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
    val TWITTER_PROVIDER = arrayListOf(AuthUI.IdpConfig.TwitterBuilder().build())
//    val FACEBOOK_PROVIDER = arrayListOf(AuthUI.IdpConfig.FacebookBuilder()
//        .setPermissions(Arrays.asList("public_profile"))
//        .build())
}