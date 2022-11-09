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

package health.care.ai.ui.adapters


import android.app.Activity
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import health.care.ai.R
import health.care.ai.api.models.medicalreport.smartreportresponse.Data
import health.care.ai.application.MainApp


class SmartReportAdapter(

    private val onItemClickListener: OnItemClickListener,
    private val mList: MutableList<Data>,
    private val activity: Activity
) :
    RecyclerView.Adapter<SmartReportAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_smart_report_row, parent, false)

        return ViewHolder(view)
    }


    class OnItemClickListener(val clickListener: (meme: Data) -> Unit) {
        fun onClick(meme: Data) = clickListener(meme)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val post = mList[position]




        holder.testName.text = post.testname


        holder.testvalue.text = post.testvalue
        holder.testUnit.text = post.testunit

        if (post.vitaldetails.size > 0) {

            holder.cardView.setOnClickListener {

                onItemClickListener.onClick(post)
            }
            holder.testNormalizedName.text = post.vitaldetails.get(0).normalizedText
            if (post.vitaldetails.get(0).normalvalues != "Undetermined") {
                holder.testNormalizedRange.text =
                    "Normal Range:-" + post.vitaldetails.get(0).normalvalues
                val array = post.vitaldetails.get(0).normalvalues.split("-")

                if (array.size == 2) {
                    val min = array[0].replace(",", "")
                    val max = array[1].replace(",", "")
                    val newvalue=post.testvalue.replace(",", "").replace("^", "")

                    if (newvalue.toDouble() < min.toDouble()) {
                        holder.testvalueStatus.text = "Low"
                        holder.testvalueStatus.setTextColor(MainApp.instance.resources.getColor(R.color.md_theme_light_error))
                    } else
                        if (newvalue.toDouble() > max.toDouble()) {
                            holder.testvalueStatus.text = "High"
                            holder.testvalueStatus.setTextColor(MainApp.instance.resources.getColor(R.color.md_theme_light_error))
                        }
                    else
                        {
                            if (newvalue.toDouble() > min.toDouble()&&newvalue.toDouble() < max.toDouble()) {
                                holder.testvalueStatus.text = "Normal"
                                holder.testvalueStatus.setTextColor(MainApp.instance.resources.getColor(R.color.green))
                            }
                            else
                            {
                                holder.testvalueStatus.text = "Undetermined"
                            }
                        }


                }


            } else {
                holder.testNormalizedName.visibility = View.GONE
                holder.testNormalizedRange.visibility = View.GONE
            }


        }



    }


    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {


        val testName: TextView = itemView.findViewById(R.id.testName)
        val testvalue: TextView = itemView.findViewById(R.id.testvalue)
        val testUnit: TextView = itemView.findViewById(R.id.testUnit)
        val testNormalizedName: TextView = itemView.findViewById(R.id.testNormalizedName)
        val testNormalizedRange: TextView = itemView.findViewById(R.id.testNormalizedRange)
        val testvalueStatus: TextView = itemView.findViewById(R.id.testvalueStatus)
        val cardView: CardView = itemView.findViewById(R.id.cardView)



    }

}
