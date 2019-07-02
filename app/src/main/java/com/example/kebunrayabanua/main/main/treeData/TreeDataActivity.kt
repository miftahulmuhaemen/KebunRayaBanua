package com.example.kebunrayabanua.main.main.treeData

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
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
            fab_changemode -> changeAdapterLayout()
        }
    }

    override fun showItems(item: List<Highlight>) {
        highlightItem.clear()
        highlightItem.addAll(item)
        recylerviewMain.adapter?.notifyDataSetChanged()
    }

    private lateinit var mainPresenter : TreeDataPresenter
    private var highlightItem: MutableList<Highlight> = mutableListOf()
    private var isGridViewAttach : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tree_data_activity)

        changeAdapterLayout()
        mainPresenter = TreeDataPresenter(this,this)
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

    private fun changeAdapterLayout(){
        if(isGridViewAttach){
            recylerviewMain.adapter = TreeDataGridAdapter(this, highlightItem) { startActivity<DetailTreeActivity>() }
            recylerviewMain.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 2)
            fab_changemode.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_grid_view))
        }
        else {
            recylerviewMain.adapter = TreeDataListAdapter(this, highlightItem) { startActivity<DetailTreeActivity>() }
            recylerviewMain.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
            fab_changemode.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_list_view))
        }
        isGridViewAttach = !isGridViewAttach
    }

}
