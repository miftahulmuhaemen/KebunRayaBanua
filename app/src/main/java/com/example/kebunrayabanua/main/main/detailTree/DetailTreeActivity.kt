package com.example.kebunrayabanua.main.main.detailTree

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.model.DataDetailTree
import com.example.kebunrayabanua.main.util.ViewpagerAdapter
import com.example.kebunrayabanua.main.util.ViewpagerAdapter.Type.DEFAULT
import com.example.kebunrayabanua.main.util.getHtml
import com.example.kebunrayabanua.main.util.gone
import com.example.kebunrayabanua.main.util.visible
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.detail_tree_activity.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

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
        if (verticalOffset <= -55)
            item_name.visible()
        else
            item_name.gone()
    }

    override fun showItems(item: DataDetailTree) {
        item_name.text = item.tanamNama
        item_latin_name.text = item.tanamNamaLatin
        item_family_name.text = item.famNama
        item_lokasi.text = item.itemLokasi
        item_register.text = item.itemNoRegister
        item_tanggal.text = item.itemTglTanam
        item_asal.text = item.itemAsal
        status_konservasi.text = item.tanamStatusKonservasi
        item_desc.text = item.tanamDeskripsiTeks
        item_info.text = item.itemInfoTanamTeks
        desc_seeMore.onClick { seeMoreAlert(item.tanamDeskripsiHtml.toString()) }
        info_seemore.onClick { seeMoreAlert(item.itemInfoTanamHtml.toString()) }
        viewPagerDetailTree.adapter = item.tanamFoto?.let { ViewpagerAdapter(DEFAULT, this, it) }
    }

    override fun errorRequest() {
        network_down.visible()
    }

    override fun onLoad() {
        progressbar.visible()
    }

    override fun finishLoad() {
        progressbar.gone()
    }

    private lateinit var presenter: DetailTreePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_tree_activity)
        val identifier = intent.getIntExtra(IS_FROM_SCANME, 0)
        val code = intent?.getStringExtra(TREE_DETAIL)
        presenter = DetailTreePresenter(this)
        presenter.getItem(code.toString(), identifier)
    }

    override fun onStart() {
        super.onStart()
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
                    textView(getHtml(text))
                }
            }
        }.show()
    }
}
