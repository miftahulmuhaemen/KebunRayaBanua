package com.example.kebunrayabanua.main.main.event

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.main.detailTree.DetailTreeActivity
import com.example.kebunrayabanua.main.model.Highlight
import com.example.kebunrayabanua.main.util.gone
import com.example.kebunrayabanua.main.util.visible
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

    override fun showItems(item: List<Highlight>) {
        highlightItem.clear()
        highlightItem.addAll(item)
        recylerviewMain.adapter?.notifyDataSetChanged()
    }

    private lateinit var mainPresenter: EventPresenter
    private var highlightItem: MutableList<Highlight> = mutableListOf()
    private var isGridViewAttach: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_activity)

        changeAdapterLayout()
        mainPresenter = EventPresenter(this, this)
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
            recylerviewMain.adapter = EventGridAdapter(this, highlightItem) { startActivity<DetailTreeActivity>() }
            recylerviewMain.layoutManager = GridLayoutManager(this, 2)
            fab_changemode.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_grid_view))
        } else {
            recylerviewMain.adapter = EventGridAdapter(this, highlightItem) { startActivity<DetailTreeActivity>() }
            recylerviewMain.layoutManager = LinearLayoutManager(this)
            fab_changemode.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_list_view))
        }
        isGridViewAttach = !isGridViewAttach
    }

}
