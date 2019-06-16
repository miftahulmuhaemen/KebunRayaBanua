package com.example.kebunrayabanua.main.main.treeData

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.main.detailTree.DetailTreeActivity
import com.example.kebunrayabanua.main.model.Highlight
import com.example.kebunrayabanua.main.util.gone
import com.example.kebunrayabanua.main.util.visible
import kotlinx.android.synthetic.main.main_activity.recylerviewMain
import kotlinx.android.synthetic.main.tree_data_activity.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity

class TreeDataActivity : AppCompatActivity(), TreeDataView,  AnkoLogger, View.OnClickListener{

    override fun onClick(v: View?) {
        when(v){
            backBtn -> finish()
        }
    }

    override fun showItems(item: List<Highlight>) {
        highlightItem.clear()
        highlightItem.addAll(item)
        mAdapter.notifyDataSetChanged()
    }

    private lateinit var mainPresenter : TreeDataPresenter
    private var highlightItem: MutableList<Highlight> = mutableListOf()
    private lateinit var mAdapter: TreeDataListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tree_data_activity)
        mAdapter = TreeDataListAdapter(this, highlightItem) {
            startActivity<DetailTreeActivity>()
        }
        recylerviewMain.adapter = mAdapter
        mainPresenter = TreeDataPresenter(this,this)
        mainPresenter.getItem()

        backBtn.setOnClickListener(this)
        searchView.setOnSearchClickListener {
            titleText.gone()
        }
        searchView.setOnCloseListener {
            titleText.visible()
            false
        }
    }

}
