package com.example.checkboxstateinrecycler.adapter

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
    private val listAdapterInteractor: ListAdapterInteractor
) : ListAdapter<ListItem, TextItemVH>(diffUtil) {

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListItem>() {

            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                oldItem.itemId == newItem.itemId

            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
                oldItem == newItem

        }

        fun newInstance(listAdapterInteractor: ListAdapterInteractor) = ListTextAdapter(
            DIFF_CALLBACK, listAdapterInteractor
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
        holder.bind(requireNotNull(getItemAt(position)), listAdapterInteractor)
    }
}

class TextItemVH(view: View) : BaseViewHolder<ListItem, ListAdapterInteractor>(view) {

    override fun onResetViewState(holder: BaseViewHolder<ListItem, ListAdapterInteractor>) {
        with(holder.view) {
            //always remove listener first then reset view to default
            checkbox.setOnCheckedChangeListener(null)
            text_item.text = ""
            checkbox.isChecked = false
        }
    }

    override fun onInitializingViewState(
        holder: BaseViewHolder<ListItem, ListAdapterInteractor>,
        data: ListItem,
        callback: ListAdapterInteractor
    ) {
        with(holder.view) {
            //always set data first then listener
            text_item.text = data.checkboxTextValue
            checkbox.isChecked = data.setCheckboxCurrentState
            //now set the state change listener event
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                data.setCheckboxCurrentState = !data.setCheckboxCurrentState
                callback.onItemClick(
                    adapterPosition,
                    data.setCheckboxCurrentState,
                    data.setCheckboxPreviousState
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

abstract class BaseViewHolder<D, I : ViewHolderInteractor>(val view: View) :
    RecyclerView.ViewHolder(view) {
    abstract fun onResetViewState(baseViewHolder: BaseViewHolder<D, I>)
    abstract fun onInitializingViewState(
        baseViewHolder: BaseViewHolder<D, I>,
        data: D,
        callback: I
    )

    fun bind(
        data: D,
        callback: I
    ) {
        onResetViewState(this)
        onInitializingViewState(this, data, callback)
    }
}

interface ViewHolderInteractor {}

interface ListAdapterInteractor : ViewHolderInteractor {
    fun onItemClick(position: Int, previousValue: Boolean, userValue: Boolean)
}
