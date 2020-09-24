package com.example.checkboxstateinrecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.checkboxstateinrecycler.adapter.ListItem
import com.example.checkboxstateinrecycler.adapter.ListTextAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var listAdapter: ListTextAdapter
    private var mockList: MutableList<ListItem> = mutableListOf<ListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onItemClick = { pos: Int, currentValue: Boolean, updatedValue: Boolean ->

            val item = listAdapter.getItemAt(pos)

            //compare the current and previous state of checkbox
            if (currentValue != updatedValue) {
                //the state is changed then add it to the list
                item?.let { mockList.add(it) }
            } else {
                //remove the item if the item is back to its previous state
                if (mockList.contains(item)) {
                    mockList.remove(item)
                }
            }

            if (item != null) Log.d("Result list", "Checked value = $item")
        }

        list_items.adapter = ListTextAdapter.newInstance(onItemClick)
            .also {
                listAdapter = it
            }

        populatedData()

        btn_save.setOnClickListener {
            if (mockList.isNotEmpty()) {
                Log.d("Result list", "Checked value = ${mockList.joinToString(",\n")}")
            }
        }

    }

    fun populatedData() {

        val data = mutableListOf<ListItem>()

        data.add(ListItem(checkboxTextValue = "Pooja", setCheckboxPreviousState =  true))
        data.add(ListItem(checkboxTextValue = "Nitin", setCheckboxPreviousState =  false))
        data.add(ListItem(checkboxTextValue = "Chetan", setCheckboxPreviousState =  false))
        data.add(ListItem(checkboxTextValue = "Alisha", setCheckboxPreviousState =  true))
        data.add(ListItem(checkboxTextValue = "Faizal", setCheckboxPreviousState =  true))
        data.add(ListItem(checkboxTextValue = "Priya", setCheckboxPreviousState =  true))
        data.add(ListItem(checkboxTextValue = "Pankaj", setCheckboxPreviousState =  false))
        data.add(ListItem(checkboxTextValue = "Shivam", setCheckboxPreviousState =  true))
        data.add(ListItem(checkboxTextValue = "HARSH", setCheckboxPreviousState =  false))
        data.add(ListItem(checkboxTextValue = "Mukesh", setCheckboxPreviousState =  false))
        data.add(ListItem(checkboxTextValue = "Prateek", setCheckboxPreviousState =  true))
        data.add(ListItem(checkboxTextValue = "Amir", setCheckboxPreviousState =  false))
        data.add(ListItem(checkboxTextValue = "Pooja", setCheckboxPreviousState =  false))
        data.add(ListItem(checkboxTextValue = "Nitin", setCheckboxPreviousState =  true))
        data.add(ListItem(checkboxTextValue = "Chetan", setCheckboxPreviousState =  true))
        data.add(ListItem(checkboxTextValue = "Alisha", setCheckboxPreviousState =  false))
        data.add(ListItem(checkboxTextValue = "Faizal", setCheckboxPreviousState =  true))
        data.add(ListItem(checkboxTextValue = "Priya", setCheckboxPreviousState =  false))
        data.add(ListItem(checkboxTextValue = "Pankaj", setCheckboxPreviousState =  false))
        data.add(ListItem(checkboxTextValue = "Shivam", setCheckboxPreviousState =  false))
        data.add(ListItem(checkboxTextValue = "HARSH", setCheckboxPreviousState =  true))
        data.add(ListItem(checkboxTextValue = "Mukesh", setCheckboxPreviousState =  true))
        data.add(ListItem(checkboxTextValue = "Prateek", setCheckboxPreviousState =  true))
        data.add(ListItem(checkboxTextValue = "Amir", setCheckboxPreviousState =  true))

        listAdapter.submitList(data)

    }

}
