package com.example.kebunrayabanua.main.util

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.kebunrayabanua.main.util.ViewpagerAdapter.Type.DEFAULT
import com.example.kebunrayabanua.main.util.ViewpagerAdapter.Type.DIALOG
import com.example.kebunrayabanua.main.util.ViewpagerAdapter.Type.HEADER
import com.github.chrisbanes.photoview.PhotoView
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.viewPager

class ViewpagerAdapter(
    private val type: Int,
    private val context: Context,
    private val images: List<String>
) : PagerAdapter(), AnkoLogger {

    object Type {
        const val DEFAULT = 0
        const val DIALOG = 1
        const val HEADER = 2
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    override fun getCount(): Int {
        return images.count()
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        var imageView = ImageView(context)
        val imageUri: String =
            if (type == HEADER) images[position]
            else getThumbnail(images[position])
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP

        when (type) {
            DIALOG -> imageView = PhotoView(context)
            DEFAULT -> {
                imageView.onClick {
                    context.alert {
                        customView {
                            verticalLayout {
                                viewPager {
                                    adapter = ViewpagerAdapter(DIALOG, context, images)
                                    currentItem = position
                                }
                            }
                        }
                        okButton {}
                    }.show()
                }
            }
        }

        Glide.with(context).load(imageUri).into(imageView)
        container.addView(imageView)
        return imageView

    }


}