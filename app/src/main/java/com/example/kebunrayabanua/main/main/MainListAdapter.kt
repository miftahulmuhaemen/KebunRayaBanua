package com.example.kebunrayabanua.main.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.model.DataEvent
import com.example.kebunrayabanua.main.util.getThumbnail
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.main_highlight_item.*

class MainListAdapter(private val context: Context, private val highlightList: List<DataEvent>, private val listener: (DataEvent) -> Unit) :
    RecyclerView.Adapter<TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(LayoutInflater.from(context).inflate(R.layout.main_highlight_item, parent, false), context)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(highlightList[position], listener)
    }

    override fun getItemCount(): Int = highlightList.size
}

class TeamViewHolder(override val containerView: View, private val context: Context) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindItem(item: DataEvent, listener: (DataEvent) -> Unit) {
        item_duration.text = "${item.eventMulai} - ${item.eventSelesai}"
        title_item.text = item.eventNama
        if (item.eventPoster?.isEmpty()!!)
            Glide.with(context).load(R.drawable.event_stock_image).into(img_item)
        else
            item.eventPoster?.first()?.let { Glide.with(context).load(getThumbnail(it)).into(img_item) }
        box_item.setOnClickListener { listener(item) }
    }

}
