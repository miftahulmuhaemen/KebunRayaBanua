package com.example.kebunrayabanua.main.main

import android.content.Context
import androidx.viewpager.widget.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import org.jetbrains.anko.AnkoLogger

class MainViewPagerAdapter(private val context: Context, private val images: IntArray) : PagerAdapter(), AnkoLogger {


    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    override fun getCount(): Int {
        return images.count()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(context).load(images[position]).into(imageView)
        container.addView(imageView)
        return imageView
    }

}