package com.example.kebunrayabanua.main.main.event

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.model.DataEvent
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.event_grid_item.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.image
import java.text.DateFormat.getDateInstance
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
        if(isExpired(item.eventSelesai.toString())){
            status_event_bg.backgroundTintList = ContextCompat.getColorStateList(context,R.color.tw__light_gray)
            status_event_text.text = "Expired"
        }
//                .transform(GrayscaleTransformation())
        box_item.setOnClickListener { listener(item) }
    }

    private fun isExpired(itemDate: String): Boolean {
        val current = Calendar.getInstance().time
        val formatter = getDateInstance()
        val date = formatter.parse(itemDate)
        return current >= date
    }


}