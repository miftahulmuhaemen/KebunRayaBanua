package com.example.kebunrayabanua.main.main.treeData

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.main.treeData.TreeDataGridAdapter.Companion.GRID_VIEW
import com.example.kebunrayabanua.main.model.DataTree
import com.example.kebunrayabanua.main.util.getThumbnail
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.tree_data_grid_item.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class TreeDataGridAdapter(
        private val context: Context,
        private val highlightList: List<DataTree>,
        private val type: Int,
        private val listener: (DataTree) -> Unit
) :
        RecyclerView.Adapter<TeamViewGridHolder>()  {

    companion object {
        const val GRID_VIEW = 1
        const val LIST_VIEW = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (type == GRID_VIEW)
            GRID_VIEW
        else
            LIST_VIEW
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewGridHolder {
        return if (type == GRID_VIEW)
            TeamViewGridHolder(LayoutInflater.from(context).inflate(R.layout.tree_data_grid_item, parent, false), context)
        else
            TeamViewGridHolder(LayoutInflater.from(context).inflate(R.layout.tree_data_list_item, parent, false), context)
    }

    override fun onBindViewHolder(gridHolder: TeamViewGridHolder, position: Int) {
        gridHolder.bindItem(gridHolder.itemViewType, highlightList[position], listener)
    }

    override fun getItemCount(): Int = highlightList.size
}

class TeamViewGridHolder(override val containerView: View, private val context: Context) :
        RecyclerView.ViewHolder(containerView), AnkoLogger,
        LayoutContainer {

    fun bindItem(type: Int, item: DataTree, listener: (DataTree) -> Unit) {
        info(item)
        if (type == GRID_VIEW) {
            if (item.tanamFoto.isNullOrEmpty())
                Glide.with(context)
                        .load(R.drawable.tree_stock_image)
                        .into(img_item)
            else
                Glide.with(context)
                        .load(getThumbnail(item.tanamFoto))
                        .into(img_item)
        }

        item_asal.text = item.itemAsal
        item_origin.text = item.itemLokasi
        item_tanggal.text = item.itemTglPenanaman
        item_latin_name.text = item.tanamNamaLatin
        item_family_name.text = item.famNama
        item_name.text = item.tanamNama
        box_item.setOnClickListener { listener(item) }
    }

}