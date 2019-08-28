package com.example.kebunrayabanua.main.util

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.kebunrayabanua.main.util.DetailViewpagerAdapter.Type.DEFAULT
import com.example.kebunrayabanua.main.util.DetailViewpagerAdapter.Type.DIALOG
import com.github.chrisbanes.photoview.PhotoView
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.viewPager

class DetailViewpagerAdapter(private val type: Int, private val context: Context, private val images: List<String>) : PagerAdapter(), AnkoLogger {

    object Type {
        const val DEFAULT = 0
        const val DIALOG = 1
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

        if (type == DIALOG)
            imageView = PhotoView(context)

        if (type == DEFAULT) {
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.onClick {
                context.alert {
                    customView {
                        verticalLayout {
                            viewPager {
                                adapter = DetailViewpagerAdapter(DIALOG, context, images)
                                currentItem = position
                            }
                        }
                    }
                    okButton {}
                }.show()
            }
        }
        Glide.with(context).load(getThumbnail(images[position])).into(imageView)
        container.addView(imageView)

        return imageView
    }


}