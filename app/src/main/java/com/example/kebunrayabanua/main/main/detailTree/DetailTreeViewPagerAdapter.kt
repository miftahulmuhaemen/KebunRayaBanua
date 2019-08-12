package com.example.kebunrayabanua.main.main.detailTree

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import org.jetbrains.anko.AnkoLogger

class DetailTreeViewPagerAdapter(private val context: Context, private val text: Array<String>) : PagerAdapter(), AnkoLogger {

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    override fun getCount(): Int {
        return text.count()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val textView = TextView(context)
        textView.text = text[position]
        container.addView(textView)
        return textView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as TextView)
    }

}