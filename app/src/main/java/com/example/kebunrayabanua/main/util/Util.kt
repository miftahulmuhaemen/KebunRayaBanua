package com.example.kebunrayabanua.main.util

import android.view.View
import com.example.kebunrayabanua.BuildConfig
import kotlinx.coroutines.Dispatchers
import java.io.IOException
import kotlin.coroutines.CoroutineContext

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

fun isOnline(): Boolean {
    val runtime = Runtime.getRuntime()
    try {
        val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
        val exitValue = ipProcess.waitFor()
        return exitValue == 0
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }

    return false
}

fun getThumbnail(name: String?): String {
    return BuildConfig.BASE_URL + "uploads/" + name
}
