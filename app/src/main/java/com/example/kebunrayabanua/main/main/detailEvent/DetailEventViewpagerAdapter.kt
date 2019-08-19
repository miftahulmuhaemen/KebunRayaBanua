package com.example.kebunrayabanua.main.main.detailEvent

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.kebunrayabanua.BuildConfig
import org.jetbrains.anko.AnkoLogger

class DetailEventViewpagerAdapter (private val context: Context, private val images: List<String>) : PagerAdapter(), AnkoLogger {

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    override fun getCount(): Int {
        return images.count()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(context).load(getThumbnail(images[position])).into(imageView)
        container.addView(imageView)
        return imageView
    }

    private fun getThumbnail(name: String?): String {
        return BuildConfig.BASE_URL + "uploads/" + name
    }

}