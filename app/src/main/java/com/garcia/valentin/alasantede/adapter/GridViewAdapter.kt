package com.garcia.valentin.alasantede.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RadioButton
import com.garcia.valentin.alasantede.R


/**
 * Created by valentin on 22/03/2019.
 */
class GridViewAdapter(private var context: Context, private var names: MutableList<String>) : BaseAdapter() {

    //class GridViewAdapter(context: Context, names: Array<String>)

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return names.count()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView

        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.gridview_layout, null)
        }
        val radioButton: RadioButton = convertView!!.findViewById(R.id.radiobutton)
        radioButton.text = names[position].toUpperCase()
        radioButton.tag = position
        return convertView
    }

}