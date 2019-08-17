package com.example.kebunrayabanua.main.main.event

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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
import org.jetbrains.anko.sdk27.coroutines.onScrollChange
import org.jetbrains.anko.startActivity

class EventActivity : AppCompatActivity(), View.OnClickListener, EventView, AnkoLogger {

    override fun onClick(v: View?) {
        when (v) {
            backBtn -> finish()
            fab_changemode -> changeAdapterLayout()
        }
    }

    override fun showItems(item: List<DataEvent>) {
        items.clear()
        items.addAll(item)
        recylerviewMain.adapter?.notifyDataSetChanged()
    }

    private lateinit var mainPresenter: EventPresenter
    private lateinit var gridAdapter: EventGridAdapter
    private lateinit var listAdapter: EventListAdapter
    private var items: MutableList<DataEvent> = mutableListOf()
    private var isGridViewAttach: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_activity)

        changeAdapterLayout()
        val apiRepository = ApiRepository()
        val gson = Gson()
        mainPresenter = EventPresenter(this, apiRepository, gson)
        mainPresenter.getItem()

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
            onQueryTextChange { query ->
                if (!isGridViewAttach)
                    gridAdapter.filter.filter(query)
//                else
//                    listAdapter.filter.filter(query)

                false
            }
        }

        recylerviewMain.onScrollChange { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if(scrollY > 0){
                
            }
        }
    }


    private fun changeAdapterLayout() {
        if (isGridViewAttach) {
            gridAdapter = EventGridAdapter(this, items) { startActivity<DetailTreeActivity>() }
            recylerviewMain.adapter = gridAdapter
            recylerviewMain.layoutManager = GridLayoutManager(this, 1)
            fab_changemode.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_grid_view))
        } else {
            listAdapter = EventListAdapter(this, items) { startActivity<DetailTreeActivity>() }
            recylerviewMain.adapter = listAdapter
            recylerviewMain.layoutManager = LinearLayoutManager(this)
            fab_changemode.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_list_view))
        }
        isGridViewAttach = !isGridViewAttach
    }

}
