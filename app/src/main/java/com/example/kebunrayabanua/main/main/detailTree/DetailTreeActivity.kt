package com.example.kebunrayabanua.main.main.detailTree

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.util.gone
import com.example.kebunrayabanua.main.util.visible
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.detail_tree_activity.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.onPageChangeListener
import org.jetbrains.anko.support.v4.viewPager

class DetailTreeActivity : AppCompatActivity(), AnkoLogger, AppBarLayout.OnOffsetChangedListener, View.OnClickListener {

    override fun onClick(v: View?) {
        when (v) {
            backBtn -> finish()
            seeMore -> seeMoreAlert()
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if(verticalOffset <= -55)
            title_bar.visible()
        else
            title_bar.gone()
    }

    private lateinit var arraySeeMore: Array<String>
    private lateinit var viewpager: ViewPager
    private lateinit var pageIndicator: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_tree_activity)
        Glide.with(this).load(R.drawable.header_2).into(header_img)
        appbar.addOnOffsetChangedListener(this)
        backBtn.setOnClickListener(this)
        seeMore.setOnClickListener(this)

        arraySeeMore = resources.getStringArray(R.array.highlight_text)
    }

    private fun seeMoreAlert() {
        alert {
            customView {
                verticalLayout {
                    padding = dip(16)
                    viewpager = viewPager().lparams(matchParent,dip(400)){
                        bottomMargin = dip(16)
                    }
                    pageIndicator = textView {
                        width = matchParent
                        gravity = Gravity.CENTER
                    }
                }
            }
        }.show()

        viewpager.adapter = DetailTreeViewPagerAdapter(this, arraySeeMore)
        viewpager.onPageChangeListener {
            onPageSelected { pageIndicator.text = "${viewpager.currentItem + 1}/${arraySeeMore.size}" }
            pageIndicator.text = "${viewpager.currentItem + 1}/${arraySeeMore.size}"
        }
    }
}
