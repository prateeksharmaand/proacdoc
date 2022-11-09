/*************************************************
 * Created by Efendi Hariyadi on 07/09/22, 12:01 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 07/09/22, 12:01 PM
 ************************************************/

package health.care.ai.ui.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import health.care.ai.R
import health.care.ai.api.models.vitalsbymajorvitalid.Data

class CustomDropDownAdapter(val context: Context, var dataSource: List<Data>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.customspinnerrow, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.txtVitalName.text = dataSource.get(position).normalizedText



        return view
    }

    override fun getItem(position: Int): Any? {
        return dataSource[position];
    }

    override fun getCount(): Int {
        return dataSource.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(row: View?) {
        val txtVitalName: TextView = row?.findViewById(R.id.txtVitalName) as TextView


    }

}