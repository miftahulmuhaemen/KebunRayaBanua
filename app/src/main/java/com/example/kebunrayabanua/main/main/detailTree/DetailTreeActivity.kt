package com.example.kebunrayabanua.main.main.detailTree

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.main.treeData.TreeDataView
import com.example.kebunrayabanua.main.model.DataDetailTree
import com.example.kebunrayabanua.main.model.DataTree
import com.example.kebunrayabanua.main.util.gone
import com.example.kebunrayabanua.main.util.visible
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.detail_tree_activity.*
import org.jetbrains.anko.*

class DetailTreeActivity : AppCompatActivity(), DetailTreeView, AnkoLogger, AppBarLayout.OnOffsetChangedListener, View.OnClickListener {

    companion object {
        const val TREE_DETAIL = "TREE_DETAIL"
        const val IS_FROM_SCANME = "SCANME"
    }

    override fun onClick(v: View?) {
        when (v) {
            backBtn -> finish()
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if(verticalOffset <= -55)
            item_name.visible()
        else
            item_name.gone()
    }


    override fun showItems(item: DataDetailTree) {
        item_name.text = item.tanamNama
        item_latin_name.text = item.tanamNamaLatin
        item_family_name.text = item.famNama

    }

    override fun errorRequest() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun closedRequest() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoad() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun finishLoad() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var presenter: DetailTreePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_tree_activity)
        presenter = DetailTreePresenter(this)

        val identifier = intent.getIntExtra(IS_FROM_SCANME, 0)
        val kode = intent?.getStringExtra(TREE_DETAIL)
        presenter.getItem(kode.toString(),identifier)

//        Glide.with(this).load(R.drawable.header_2).into(header_img)

        appbar.addOnOffsetChangedListener(this)
        backBtn.setOnClickListener(this)
        desc_seeMore.setOnClickListener(this)
        info_seemore.setOnClickListener(this)
    }

    private fun seeMoreAlert(text: String) {
        alert {
            customView {
                verticalLayout {
                    padding = dip(32)
                    textView(text)
                }
            }
        }.show()
    }
}
