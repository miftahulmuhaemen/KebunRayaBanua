package com.example.kebunrayabanua.main.util

import android.view.View
import com.example.kebunrayabanua.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

open class CoroutineContextProvider {
    open val main: CoroutineContext by lazy { Dispatchers.Main }
}

suspend fun isOnline(): Boolean = withContext(Dispatchers.Default) {
    val runtime = Runtime.getRuntime()
    try {
        val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
        val exitValue = ipProcess.waitFor()
        return@withContext exitValue == 0
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
    return@withContext false
}


fun getThumbnail(name: String?): String {
    return BuildConfig.BASE_URL + "uploads/" + name
}
