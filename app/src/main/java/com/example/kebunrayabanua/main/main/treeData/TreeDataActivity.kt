package com.example.kebunrayabanua.main.main.treeData

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.main.detailTree.DetailTreeActivity
import com.example.kebunrayabanua.main.main.detailTree.DetailTreeActivity.Companion.IS_FROM_SCANME
import com.example.kebunrayabanua.main.main.detailTree.DetailTreeActivity.Companion.TREE_DETAIL
import com.example.kebunrayabanua.main.main.treeData.TreeDataGridAdapter.Companion.GRID_VIEW
import com.example.kebunrayabanua.main.main.treeData.TreeDataGridAdapter.Companion.LIST_VIEW
import com.example.kebunrayabanua.main.model.DataTree
import com.example.kebunrayabanua.main.util.gone
import com.example.kebunrayabanua.main.util.visible
import kotlinx.android.synthetic.main.tree_data_activity.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.appcompat.v7.coroutines.onQueryTextListener
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast

class TreeDataActivity : AppCompatActivity(), TreeDataView, AnkoLogger, View.OnClickListener {

    companion object {
        const val findAll = "all"
    }

    override fun onClick(v: View?) {
        when (v) {
            backBtn -> finish()
            fab_changemode -> changeAdapterLayout()
        }
    }

    override fun showItems(item: List<DataTree>) {
        if (!swipe.isRefreshing or items.isEmpty()) {
            items.addAll(item)
            recylerviewMain.adapter?.notifyDataSetChanged()
        } else
            if (items.first() != item.first()) {
                isRequestEnd = false
                items.clear()
                items.addAll(item)
                recylerviewMain.adapter?.notifyDataSetChanged()
            }
        network_down.gone()
        swipe.isRefreshing = false
    }

    override fun errorRequest() {
        swipe.isRefreshing = false
        if (items.isEmpty())
            network_down.visible()
        else
            toast("Terjadi masalah.")
    }

    override fun closedRequest() {
        isRequestEnd = true
    }

    override fun onLoad() {
        progressbar.visible()
    }

    override fun finishLoad() {
        progressbar.gone()
    }

    private lateinit var treePresenter: TreeDataPresenter
    private var items: MutableList<DataTree> = mutableListOf()
    private var isGridViewAttach: Boolean = false
    private var isRequestEnd: Boolean = false
    private var pageNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tree_data_activity)

        changeAdapterLayout()
        treePresenter = TreeDataPresenter(this)
        treePresenter.getItem(pageNumber, findAll)

        backBtn.setOnClickListener(this)
        fab_changemode.setOnClickListener(this)
        searchView.setOnSearchClickListener {
            titleText.gone()
        }
        searchView.setOnCloseListener {
            titleText.visible()
            false
        }
        searchView.onQueryTextListener {
            onQueryTextSubmit { query ->
                pageNumber = 0
                items.clear()
                treePresenter.getItem(pageNumber, query.toString())
                false
            }
        }
        swipe.onRefresh {
            pageNumber = 0
            treePresenter.getItem(pageNumber, findAll)
        }
        recylerviewMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (linearLayoutManager!!.itemCount <= linearLayoutManager.findLastVisibleItemPosition() + 1 && !isRequestEnd) {
                    pageNumber += 15
                    treePresenter.getItem(pageNumber, findAll)
                }
            }
        })
    }

    private fun changeAdapterLayout() {
        if (!isGridViewAttach) {
            recylerviewMain.adapter = TreeDataGridAdapter(this, items, GRID_VIEW) {
                startActivity<DetailTreeActivity>(
                        TREE_DETAIL to it.itemKode,
                        IS_FROM_SCANME to 0
                )
            }
            recylerviewMain.layoutManager = GridLayoutManager(this, 2)
            fab_changemode.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_grid_view))
        } else {
            recylerviewMain.adapter = TreeDataGridAdapter(this, items, LIST_VIEW) {
                startActivity<DetailTreeActivity>(
                        TREE_DETAIL to it.itemKode,
                        IS_FROM_SCANME to 0
                )
            }
            recylerviewMain.layoutManager = LinearLayoutManager(this)
            fab_changemode.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_list_view))
        }
        isGridViewAttach = !isGridViewAttach
    }

}
