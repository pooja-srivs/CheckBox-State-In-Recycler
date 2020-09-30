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

class TextItemVH(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(
        textListItem: ListItem,
        onItemClick: (Int, Boolean, Boolean) -> Unit
    ) {

        //ListView automatically calls onCheckedChanged when scrolling
        //CheckBox state and setting the state change listener event
        with(view){
            text_item.text = textListItem.checkboxTextValue
            checkbox.isChecked = textListItem.setCheckboxCurrentState

            //now set the state change listener event
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                Log.e("checkbox","called $isChecked at pos: $adapterPosition")
                textListItem.setCheckboxCurrentState = isChecked

                onItemClick.invoke(
                    adapterPosition,
                    textListItem.setCheckboxCurrentState,
                    textListItem.setCheckboxPreviousState
                )
            }
        }



    }
}

data class ListItem(
    val itemId: Int = (0..100).random(),
    val checkboxTextValue: String,
    val setCheckboxPreviousState: Boolean,
    var setCheckboxCurrentState: Boolean = setCheckboxPreviousState
)