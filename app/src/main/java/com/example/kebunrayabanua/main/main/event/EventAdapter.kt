package com.example.kebunrayabanua.main.main.event

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.model.DataEvent
import com.example.kebunrayabanua.main.util.getThumbnail
import jp.wasabeef.glide.transformations.GrayscaleTransformation
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.event_grid_item.*
import org.jetbrains.anko.AnkoLogger
import java.text.DateFormat.getDateInstance
import java.util.*

class EventGridAdapter(
        private val context: Context,
        private var items: List<DataEvent>,
        private val listener: (DataEvent) -> Unit
) : RecyclerView.Adapter<TeamViewGridHolder>(), Filterable, AnkoLogger {

    private val originalItem: List<DataEvent> = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewGridHolder {
        return TeamViewGridHolder(LayoutInflater.from(context).inflate(R.layout.event_grid_item, parent, false), context)
    }

    override fun onBindViewHolder(gridHolder: TeamViewGridHolder, position: Int) {
        gridHolder.bindItem(items[position], listener)
    }

    override fun getItemCount(): Int = items.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                items = if (charString.isEmpty())
                    originalItem
                else {
                    val filteredList = ArrayList<DataEvent>()
                    for (row in items)
                        if (row.eventNama!!.toLowerCase().contains(charString.toLowerCase()))
                            filteredList.add(row)
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = items
                return filterResults
            }
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                items = results?.values as ArrayList<DataEvent>
                notifyDataSetChanged()
            }
        }
    }
}

class TeamViewGridHolder(override val containerView: View, private val context: Context) :
    RecyclerView.ViewHolder(containerView), AnkoLogger,
    LayoutContainer {

    @SuppressLint("SetTextI18n")
    fun bindItem(item: DataEvent, listener: (DataEvent) -> Unit) {
        item_name.text = item.eventNama
        item_duration.text = "${item.eventMulai} - ${item.eventSelesai}"
        if(isExpired(item.eventSelesai.toString())){
            status_event_bg.backgroundTintList = ContextCompat.getColorStateList(context,R.color.tw__light_gray)
            status_event_text.text = context.getString(R.string.expired)
            if (item.eventPoster.isNullOrEmpty())
                Glide.with(context)
                        .load(R.drawable.event_stock_image)
                        .transform(GrayscaleTransformation())
                        .into(img_item)
            else
                Glide.with(context)
                        .load(getThumbnail(item.eventPoster?.first()))
                        .transform(GrayscaleTransformation())
                        .into(img_item)
        } else {
            status_event_bg.backgroundTintList = ContextCompat.getColorStateList(context,R.color.colorPrimary)
            status_event_text.text = context.getString(R.string.active)
            if (item.eventPoster?.isEmpty()!!)
                Glide.with(context)
                        .load(R.drawable.event_stock_image)
                        .into(img_item)
            else
                Glide.with(context)
                        .load(getThumbnail(item.eventPoster?.first()))
                        .into(img_item)
        }
        box_item.setOnClickListener { listener(item) }
    }

    private fun isExpired(itemDate: String): Boolean {
        val current = Calendar.getInstance().time
        val formatter = getDateInstance()
        val date = formatter.parse(itemDate)
        return current > date
    }

}