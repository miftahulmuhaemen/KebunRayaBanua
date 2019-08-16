package com.example.kebunrayabanua.main.main.event

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.api.ApiRepository
import com.example.kebunrayabanua.main.main.detailTree.DetailTreeActivity
import com.example.kebunrayabanua.main.model.DataEvent
import com.example.kebunrayabanua.main.util.gone
import com.example.kebunrayabanua.main.util.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.tree_data_activity.*
import org.jetbrains.anko.AnkoLogger
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
    }


    private fun changeAdapterLayout() {
        if (isGridViewAttach) {
            recylerviewMain.adapter = EventGridAdapter(this, items) { startActivity<DetailTreeActivity>() }
            recylerviewMain.layoutManager = GridLayoutManager(this, 1)
            fab_changemode.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_grid_view))
        } else {
//            recylerviewMain.adapter = EventGridAdapter(this, items) { startActivity<DetailTreeActivity>() }
//            recylerviewMain.layoutManager = LinearLayoutManager(this)
//            fab_changemode.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_list_view))
        }
        isGridViewAttach = !isGridViewAttach
    }

}
