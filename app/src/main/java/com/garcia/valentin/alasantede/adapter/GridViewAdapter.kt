package com.garcia.valentin.alasantede.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RadioButton
import android.widget.TextView
import com.garcia.valentin.alasantede.R


/**
 * Created by valentin on 22/03/2019.
 */
class GridViewAdapter(context: Context, names: MutableList<String>) : BaseAdapter() {

    private var context = context
    private var names = names
    var selectedPosition = -1
    private val selectedRB: RadioButton? = null

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
        radioButton.text = names.get(position)
        radioButton.tag = names.get(position)
        return convertView
    }

}