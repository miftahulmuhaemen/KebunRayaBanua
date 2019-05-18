package com.example.kebunrayabanua.LoginActivity.mainActivity

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.kebunrayabanua.LoginActivity.model.hightlight
import com.example.kebunrayabanua.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_main_highlight_item.*

class MainActivityListAdapter(private val context : Context, private val highlightList: List<hightlight>, private val listener: (hightlight) -> Unit) :
    RecyclerView.Adapter<TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_main_highlight_item, parent, false), context)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(highlightList[position], listener)
    }

    override fun getItemCount(): Int = highlightList.size
}

class TeamViewHolder(override val containerView: View, val context: Context) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindItem(item: hightlight, listener: (hightlight) -> Unit) {
        title_item.text = item.name
        item.img?.let { Glide.with(context).load(it).into(img_item) }
        box_item.setOnClickListener { listener(item) }
    }

}
