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
import com.example.kebunrayabanua.BuildConfig
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.model.DataEvent
import jp.wasabeef.glide.transformations.GrayscaleTransformation
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.event_grid_item.*
import org.jetbrains.anko.AnkoLogger
import java.text.DateFormat.getDateInstance
import java.util.*


class EventGridAdapter(
        private val context: Context,
        private var items: List<DataEvent>,
        private val adapterType: Int = 1,
        private val listener: (DataEvent) -> Unit
) :
        RecyclerView.Adapter<TeamViewGridHolder>(), Filterable, AnkoLogger {

    private val originalItem: List<DataEvent> = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewGridHolder {
//        return when (adapterType) {
//            GRID_VIEW -> {
              return  TeamViewGridHolder(LayoutInflater.from(context).inflate(R.layout.event_grid_item, parent, false), context)
//            }
//            LIST_VIEW -> {
//                TeamViewGridHolder(LayoutInflater.from(context).inflate(R.layout.event_list_item, parent, false), context)
//            }
//            else -> {
//                TeamViewGridHolder(LayoutInflater.from(context).inflate(R.layout.app_loading_item, parent, false), context)
//            }
//        }
    }

    override fun onBindViewHolder(gridHolder: TeamViewGridHolder, position: Int) {
        gridHolder.bindItem(items[position], listener)
    }

    override fun getItemCount(): Int = items.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                if (charString.isEmpty())
                    items = originalItem
                else {
                    val filteredList = ArrayList<DataEvent>()
                    for (row in items)
                        if (row.eventNama!!.toLowerCase().contains(charString.toLowerCase()))
                            filteredList.add(row)
                    items = filteredList
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
    RecyclerView.ViewHolder(containerView),
    LayoutContainer, AnkoLogger {

    @SuppressLint("SetTextI18n")
    fun bindItem(item: DataEvent, listener: (DataEvent) -> Unit) {
        item_name.text = item.eventNama
        item_duration.text = "${item.eventMulai} - ${item.eventSelesai}"
        if(isExpired(item.eventSelesai.toString())){
            status_event_bg.backgroundTintList = ContextCompat.getColorStateList(context,R.color.tw__light_gray)
            status_event_text.text = context.getString(R.string.expired)
            if (item.eventPoster?.isNotEmpty()!!)
                Glide.with(context)
                        .load(getThumbnail(item.eventPoster?.first().toString()))
                        .transform(GrayscaleTransformation())
                        .into(img_item)
        } else {
            if (item.eventPoster?.isNotEmpty()!!)
                Glide.with(context)
                        .load(getThumbnail(item.eventPoster?.first().toString()))
                        .into(img_item)
        }
        box_item.setOnClickListener { listener(item) }
    }

    private fun isExpired(itemDate: String): Boolean {
        val current = Calendar.getInstance().time
        val formatter = getDateInstance()
        val date = formatter.parse(itemDate)
        return current >= date
    }

    private fun getThumbnail(name: String): String? {
        return BuildConfig.BASE_URL + "uploads/" + name
    }


}