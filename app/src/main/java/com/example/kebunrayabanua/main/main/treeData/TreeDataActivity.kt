package com.example.kebunrayabanua.main.main.treeData

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.main.MainListAdapter
import com.example.kebunrayabanua.main.main.MainPresenter
import com.example.kebunrayabanua.main.model.Highlight
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_activity.recylerviewMain
import kotlinx.android.synthetic.main.tree_data_activity.*
import org.jetbrains.anko.toast

class TreeDataActivity : AppCompatActivity(), TreeDataView {

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
            toast(it.name.toString())
        }
        backBtn.setOnClickListener {finish()}
        recylerviewMain.adapter = mAdapter
        mainPresenter = TreeDataPresenter(this,this)
        mainPresenter.getItem()
    }
}
