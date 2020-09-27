package com.example.checkboxstateinrecycler.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.checkboxstateinrecycler.R
import kotlinx.android.synthetic.main.list_items.view.*


class ListTextAdapter private constructor(
    private val diffUtil: DiffUtil.ItemCallback<ListItem>,
    private val onItemClick: (/*currentPosition*/Int,/*previousState*/ Boolean,/*updatedState*/ Boolean) -> Unit
) : ListAdapter<ListItem, TextItemVH>(diffUtil) {

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListItem>() {

            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                oldItem.itemId == newItem.itemId

            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                oldItem == newItem

        }

        fun newInstance(onItemClick: (Int, Boolean, Boolean) -> Unit) = ListTextAdapter(
            DIFF_CALLBACK, onItemClick
        )
    }

    fun getItemAt(position: Int): ListItem? {
        return getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemVH {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_items, parent, false)
        return TextItemVH(view)
    }

    override fun onBindViewHolder(holder: TextItemVH, position: Int) {
        holder.bind(requireNotNull(getItemAt(position)), onItemClick)
    }
}

class TextItemVH(view: View) : RecyclerView.ViewHolder(view) {

    var data: ListItem? = null
    var onItemClick: (Int, Boolean, Boolean) -> Unit = { pos, lastvalue, currentvalue -> }

    init {
        with(view) {
            //you need to attach listener once globally
            //At runtime global reference will change due to onBind and you will have right data
            // this approach isn't recommended as it holds listener of pervious states of ViewHolder
            // even thou the data is getting refereshed it can cause leaks in memory as reference
            // of view is never released
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                data?.setCheckboxCurrentState = isChecked
                onItemClick.invoke(
                    adapterPosition,
                    data?.setCheckboxCurrentState ?: false,
                    data?.setCheckboxPreviousState ?: false
                )
            }
        }
    }

    fun bind(
        textListItem: ListItem,
        onItemClick: (Int, Boolean, Boolean) -> Unit
    ) {
        this.data = textListItem
        this.onItemClick = onItemClick
        with(itemView) {
            text_item.text = textListItem.checkboxTextValue
            checkbox.isChecked = textListItem.setCheckboxCurrentState
        }
    }

}

data class ListItem(
    val itemId: Int = (0..100).random(),
    val checkboxTextValue: String,
    val setCheckboxPreviousState: Boolean,
    var setCheckboxCurrentState: Boolean = setCheckboxPreviousState
)