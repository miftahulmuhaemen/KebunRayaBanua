package com.example.kebunrayabanua.main.main.treeData

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.model.Highlight
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.main_highlight_item.box_item
import kotlinx.android.synthetic.main.main_highlight_item.img_item
import kotlinx.android.synthetic.main.tree_data_item.*


class TreeDataListAdapter(private val context : Context, private val highlightList: List<Highlight>, private val listener: (Highlight) -> Unit) :
    RecyclerView.Adapter<TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(LayoutInflater.from(context).inflate(R.layout.tree_data_item, parent, false), context)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(highlightList[position], listener)
    }

    override fun getItemCount(): Int = highlightList.size
}

class TeamViewHolder(override val containerView: View, private val context: Context) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bindItem(item: Highlight, listener: (Highlight) -> Unit) {
        item_name.text = item.name
        item_latin_name.text = item.name
        item.img?.let { Glide.with(context).load(it).into(img_item) }
        box_item.setOnClickListener { listener(item) }
    }

}