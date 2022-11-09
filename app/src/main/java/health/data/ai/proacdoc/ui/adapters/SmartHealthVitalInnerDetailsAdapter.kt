/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 12:26 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 12:26 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 02/06/22, 4:14 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 02/06/22, 4:14 PM
 ************************************************/

package health.data.ai.proacdoc.ui.adapters


import android.app.Activity
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import health.care.ai.proacdoc.R

import health.data.ai.proacdoc.api.models.smarthealthresponse.Medicalrecord


import health.data.ai.proacdoc.application.MainApp
import health.data.ai.proacdoc.utils.Utils.CompanionClass.Companion.ifNonNull


class SmartHealthVitalInnerDetailsAdapter(

    private val onItemClickListener: OnItemClickListener,
    private val mList: MutableList<Medicalrecord>,
    private val activity: Activity,
    private val normalvalues: String

) :
    RecyclerView.Adapter<SmartHealthVitalInnerDetailsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.vitalinnerdetails_row, parent, false)

        return ViewHolder(view)
    }


    class OnItemClickListener(val clickListener: (meme: Medicalrecord) -> Unit) {
        fun onClick(meme: Medicalrecord) = clickListener(meme)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val post = mList[position]
        holder.cardView.setOnClickListener(View.OnClickListener {
            onItemClickListener.onClick(post)
        })
        holder.testDate.text = "Date not Available"
        post.dated.ifNonNull {

            holder.testDate.text = "Date " +post.dated
        }
        holder.testdetailName.text = post.testname

        holder.testvalue.text = post.testvalue + " " + post.testunit

        val array = normalvalues.split("-")

        if (array.size == 2) {
            val min = array[0].replace(",", "")
            val max = array[1].replace(",", "")
            val newvalue = post.testvalue.replace(",", "").replace("^", "")

            if (newvalue.toDouble() < min.toDouble()) {
                holder.testStatus.text = "Low"
                holder.testStatus.setTextColor(MainApp.instance.resources.getColor(R.color.md_theme_light_error))
            } else
                if (newvalue.toDouble() > max.toDouble()) {
                    holder.testStatus.text = "High"
                    holder.testStatus.setTextColor(MainApp.instance.resources.getColor(R.color.md_theme_light_error))
                } else {
                    if (newvalue.toDouble() > min.toDouble() && newvalue.toDouble() < max.toDouble()) {
                        holder.testStatus.text = "Normal"
                        holder.testStatus.setTextColor(MainApp.instance.resources.getColor(R.color.green))
                    } else {
                        holder.testStatus.text = "N/A"
                    }
                }


        }


    }


    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val testdetailName: TextView = itemView.findViewById(R.id.testdetailName)
        val testStatus: TextView = itemView.findViewById(R.id.testStatus)

        val testDate: TextView = itemView.findViewById(R.id.testDate)
        val testvalue: TextView = itemView.findViewById(R.id.testvalue)
        val cardView: CardView = itemView.findViewById(R.id.cardView)


    }

}
