package com.example.kebunrayabanua.main.main.event

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.api.RetrofitFactory
import com.example.kebunrayabanua.main.api.RetrofitService
import com.example.kebunrayabanua.main.main.detailEvent.DetailEventActivity
import com.example.kebunrayabanua.main.model.DataEvent
import com.example.kebunrayabanua.main.util.gone
import com.example.kebunrayabanua.main.util.visible
import kotlinx.android.synthetic.main.detail_event_activity.backBtn
import kotlinx.android.synthetic.main.event_activity.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.appcompat.v7.coroutines.onQueryTextListener
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast

class EventActivity : AppCompatActivity(), View.OnClickListener, EventView, AnkoLogger {

    override fun onClick(v: View?) {
        when (v) {
            backBtn -> finish()
        }
    }

    override fun showItems(item: List<DataEvent>) {
        if (!swipe.isRefreshing or items.isEmpty()) {
            items.addAll(item)
            recylerviewMain.adapter?.notifyDataSetChanged()
        } else {
            if (items.first() != item.first()) {
                isRequestEnd = false
                items.clear()
                items.addAll(item)
                recylerviewMain.adapter?.notifyDataSetChanged()
            }
        }
        network_down.gone()
        swipe.isRefreshing = false
    }

    override fun closedRequest() {
        isRequestEnd = true
    }

    override fun errorRequest() {
        swipe.isRefreshing = false
        if(items.isEmpty()){
            network_down.visible()
        } else {
            toast("Terjadi masalah.")
        }
    }


    private lateinit var eventPresenter: EventPresenter
    private lateinit var gridAdapter: EventGridAdapter
    private var items: MutableList<DataEvent> = mutableListOf()
    private var isRequestEnd: Boolean = false
    private var pageNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_activity)

        eventPresenter = EventPresenter(this)

        gridAdapter = EventGridAdapter(this, items) { startActivity<DetailEventActivity>(DetailEventActivity.DETAIL_EVENT to it) }
        recylerviewMain.adapter = gridAdapter
        recylerviewMain.layoutManager = GridLayoutManager(this, 1)
        eventPresenter.getItem(pageNumber)

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
            pageNumber = 0; eventPresenter.getItem(pageNumber)
        }
        recylerviewMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (linearLayoutManager!!.itemCount <= linearLayoutManager.findLastVisibleItemPosition() + 1 && !isRequestEnd) {
                    pageNumber += 15
                    eventPresenter.getItem(pageNumber)
                }
            }
        })
    }

}
