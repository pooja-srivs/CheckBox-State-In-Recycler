package com.example.airtel.checkbox.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.checkboxstateinrecycler.R
import kotlinx.android.synthetic.main.list_items.view.*

class ListTextAdapter private constructor(
    private val diffUtil: DiffUtil.ItemCallback<ListItem>,
    private val onItemClick: (Int, Boolean, Boolean) -> Unit
) : ListAdapter<ListItem, TextItemVH>(diffUtil){

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListItem>() {

            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                oldItem.checkboxTextValue == newItem.checkboxTextValue

            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                oldItem == newItem

        }

        fun newInstance(onItemClick : (Int, Boolean, Boolean) -> Unit) = ListTextAdapter(
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

class TextItemVH(view : View) : RecyclerView.ViewHolder(view){

    val text_movie_item = view.text_item
    val item_checkbox = view.checkbox

    fun bind(
        textListItem: ListItem,
        onItemClick: (Int, Boolean, Boolean) -> Unit
    ){

        //ListView automatically calls onCheckedChanged when scrolling
        //Set the state change listener event to null before initializing the
        //CheckBox state and setting the state change listener event
        item_checkbox.setOnCheckedChangeListener(null)
        text_movie_item.setText(textListItem.checkboxTextValue)

        item_checkbox.isChecked = textListItem.setCheckboxCurrentState

        //now set the state change listener event
        item_checkbox.setOnCheckedChangeListener{buttonView, isChecked ->

            textListItem.setCheckboxCurrentState = isChecked

            onItemClick.invoke(adapterPosition, textListItem.setCheckboxCurrentState, textListItem.setCheckboxPreviousState)
        }

    }
}

data class ListItem(val checkboxTextValue: String, val setCheckboxPreviousState: Boolean, var setCheckboxCurrentState: Boolean = setCheckboxPreviousState)