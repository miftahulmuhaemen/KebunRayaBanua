package com.example.kebunrayabanua.main.main.event

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.model.DataEvent
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.event_grid_item.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class EventGridAdapter(
        private val context: Context,
        private val items: List<DataEvent>,
        private val listener: (DataEvent) -> Unit
) :
    RecyclerView.Adapter<TeamViewGridHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewGridHolder {
        return TeamViewGridHolder(
            LayoutInflater.from(context).inflate(R.layout.event_grid_item, parent, false),
            context
        )
    }

    override fun onBindViewHolder(gridHolder: TeamViewGridHolder, position: Int) {
        gridHolder.bindItem(items[position], listener)
    }

    override fun getItemCount(): Int = items.size
}

class TeamViewGridHolder(override val containerView: View, private val context: Context) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer, AnkoLogger {

    fun bindItem(item: DataEvent, listener: (DataEvent) -> Unit) {
        item_name.text = item.eventNama
        item_duration.text = "${item.eventMulai} - ${item.eventSelesai}"

getExpireDate(item.eventSelesai.toString())
//        item_name.text = item.name
//        item_latin_name.text = item.name
//        item.img?.let { Glide.with(context).load(it)
//                .transform(GrayscaleTransformation())
//                .into(img_item) }
//        box_item.setOnClickListener { listener(item) }
    }

    fun getExpireDate(itemDate: String): Boolean {

        val current = Calendar.getInstance().time
        val formatter =  SimpleDateFormat("dd MMMM yyyy")
        val date = formatter.parse(itemDate)
        if(current >= date) return true



//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val current = LocalDate.now()
//            val compareItemDate = LocalDate.parse(itemDate, DateTimeFormatter.ofPattern("dd MMMM yyyy"))
//            compareItemDate.compareTo(current)
//        } else {
//            val current = Calendar.getInstance()
//            val date = Date()
//            val formatter = SimpleDateFormat("dd MMMM yyyy")
//            formatter.format(date)
//        }

        return true
    }


}