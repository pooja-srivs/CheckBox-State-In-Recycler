package com.example.checkboxstateinrecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.airtel.checkbox.adapter.ListItem
import com.example.airtel.checkbox.adapter.ListTextAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var listAdapter : ListTextAdapter
    private var arrList : MutableList<ListItem> = mutableListOf<ListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onItemClick = {pos: Int, currentValue: Boolean, isChecked: Boolean ->
            val itempos = listAdapter.getItemAt(pos)

            //compare the current and previous state of checkbox
            //if -> the state is changed then add it to the list
            //else -> remove the item if the item is back to its previous state
            if (currentValue != isChecked){
                itempos?.let { arrList.add(it) }
            }else{
                if (arrList.contains(itempos)){
                    arrList.remove(itempos)
                }
            }

            if (itempos != null) System.out.println("Checked value = $itempos")
        }

        list_items.adapter = ListTextAdapter.newInstance(onItemClick)
            .also {
                listAdapter = it
            }

        populatedData()

        btn_save.setOnClickListener {
            if (!arrList.isEmpty()){
                arrList.forEach { data ->
                    System.out.println(data)
                }
            }
        }

    }

    fun populatedData(){

        val data = mutableListOf<ListItem>()

        data.add(ListItem("Pooja", true))
        data.add(ListItem("Nitin", true))
        data.add(ListItem("Chetan", true))
        data.add(ListItem("Alisha", false))
        data.add(ListItem("Faizal", true))
        data.add(ListItem("Priya", true))
        data.add(ListItem("Pankaj", true))
        data.add(ListItem("Shivam", true))
        data.add(ListItem("Natasha", false))
        data.add(ListItem("Shikha", false))
        data.add(ListItem("Harsh", false))
        data.add(ListItem("Mukesh", false))
        data.add(ListItem("Prateek", false))
        data.add(ListItem("Amir", true))
        data.add(ListItem("Neeru", false))
        data.add(ListItem("Vibha", false))
        data.add(ListItem("Shailja", false))
        data.add(ListItem("Bhagyashree", false))
        data.add(ListItem("Ritu", true))
        data.add(ListItem("Neelu", false))

        listAdapter.submitList(data)

    }

}
