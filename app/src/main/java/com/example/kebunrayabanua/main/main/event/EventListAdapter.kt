package com.example.kebunrayabanua.main.main.event


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.model.Highlight
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.event_grid_item.*
import kotlinx.android.synthetic.main.main_highlight_item.box_item
import kotlinx.android.synthetic.main.tree_data_list_item.*
import kotlinx.android.synthetic.main.tree_data_list_item.item_name


class EventListAdapter(
    private val context: Context,
    private val highlightList: List<Highlight>,
    private val listener: (Highlight) -> Unit
) :
    RecyclerView.Adapter<TeamViewListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewListHolder {
        return TeamViewListHolder(
            LayoutInflater.from(context).inflate(
                R.layout.event_list_item,
                parent,
                false
            ), context
        )
    }

    override fun onBindViewHolder(gridHolder: TeamViewListHolder, position: Int) {
        gridHolder.bindItem(highlightList[position], listener)
    }

    override fun getItemCount(): Int = highlightList.size
}

class TeamViewListHolder(override val containerView: View, private val context: Context) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bindItem(item: Highlight, listener: (Highlight) -> Unit) {
        item_name.text = item.name
        item_duration.text = item.name
        item_desc.text = item.name
        box_item.setOnClickListener { listener(item) }
    }

}