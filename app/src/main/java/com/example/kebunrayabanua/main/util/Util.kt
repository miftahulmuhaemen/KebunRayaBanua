package com.example.kebunrayabanua.main.util

import android.view.View
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

object Util {
    const val RC_SIGN_IN: Int = 1
    val GOOGLE_PROVIDER = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
    val TWITTER_PROVIDER = arrayListOf(AuthUI.IdpConfig.TwitterBuilder().build())
}

object Permission {
    const val SCAN = 100
    const val WHEREIAM = 100
}

object IntervalTime {
    const val UPDATE_INTERVAL: Long = 100 * 1000
    const val FASTEST_INTERVAL: Long = 5000
}

object RecylerviewViewIdentifier {
    const val GRID_VIEW = 1
    const val LIST_VIEW = 2
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

open class CoroutineContextProvider {
    open val main: CoroutineContext by lazy { Dispatchers.Main }
}
