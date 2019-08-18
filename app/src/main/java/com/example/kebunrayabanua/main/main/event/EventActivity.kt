package com.example.kebunrayabanua.main.main.event

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.api.ApiRepository
import com.example.kebunrayabanua.main.main.detailTree.DetailTreeActivity
import com.example.kebunrayabanua.main.model.DataEvent
import com.example.kebunrayabanua.main.util.gone
import com.example.kebunrayabanua.main.util.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.detail_event_activity.backBtn
import kotlinx.android.synthetic.main.event_activity.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.appcompat.v7.coroutines.onQueryTextListener
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh

class EventActivity : AppCompatActivity(), View.OnClickListener, EventView, AnkoLogger {

    override fun onClick(v: View?) {
        when (v) {
            backBtn -> finish()
        }
    }

    override fun showItems(item: List<DataEvent>) {
        if (!swipe.isRefreshing) {
            items.addAll(item)
            recylerviewMain.adapter?.notifyDataSetChanged()
        } else {
            if (items.first() != item.first()) {
                isRequestEnd = false
                items.clear()
                items.addAll(item)
                recylerviewMain.adapter?.notifyDataSetChanged()
            }
            swipe.isRefreshing = false
        }
    }

    override fun closedRequest() {
        isRequestEnd = true
    }

    private lateinit var mainPresenter: EventPresenter
    private lateinit var gridAdapter: EventGridAdapter
    private var items: MutableList<DataEvent> = mutableListOf()
    private var isRequestEnd: Boolean = false
    private var pageNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_activity)
        mainPresenter = EventPresenter(this, ApiRepository(), Gson())

        gridAdapter = EventGridAdapter(this, items) { startActivity<DetailTreeActivity>() }
        recylerviewMain.adapter = gridAdapter
        recylerviewMain.layoutManager = GridLayoutManager(this, 1)
        mainPresenter.getItem(pageNumber)

        backBtn.setOnClickListener(this)
        searchView.setOnSearchClickListener {
            titleText.gone()
        }
        searchView.setOnCloseListener {
            titleText.visible(); false
        }
        searchView.onQueryTextListener {
            onQueryTextChange { query -> gridAdapter.filter.filter(query); false }
        }
        swipe.onRefresh {
            pageNumber = 0; mainPresenter.getItem(pageNumber)
        }
        recylerviewMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (linearLayoutManager!!.itemCount <= linearLayoutManager.findLastVisibleItemPosition() + 1 && !isRequestEnd) {
                    pageNumber += 5
                    mainPresenter.getItem(pageNumber)
                }
            }
        })
    }

}
