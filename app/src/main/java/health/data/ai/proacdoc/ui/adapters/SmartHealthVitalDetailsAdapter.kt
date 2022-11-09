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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import health.care.ai.proacdoc.R

import health.data.ai.proacdoc.api.models.smarthealthresponse.Medicalrecord
import health.data.ai.proacdoc.api.models.smarthealthresponse.Vitaldetails


class SmartHealthVitalDetailsAdapter(

    private val onItemClickListener: OnItemClickListener,
    private val mList: MutableList<Vitaldetails>,
    private val activity: Activity
) :
    RecyclerView.Adapter<SmartHealthVitalDetailsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_smart_health_details_row, parent, false)

        return ViewHolder(view)
    }


    class OnItemClickListener(val clickListener: (meme: Vitaldetails) -> Unit) {
        fun onClick(meme: Vitaldetails) = clickListener(meme)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val post = mList[position]
        holder.cardView.setOnClickListener(View.OnClickListener {
            onItemClickListener.onClick(post)
        })

        holder.testName.text = post.normalizedText
        holder.testRange.text = "Normal Range " + post.normalvalues

        var labdetailsList = ArrayList<Medicalrecord>()
        if (!post.medicalrecord.isNullOrEmpty()) {

            for (vitaldetail in post.medicalrecord) {
                labdetailsList.add(vitaldetail)
            }


            holder.recyclerViewInner.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)


            holder.recyclerViewInner.adapter =
                SmartHealthVitalInnerDetailsAdapter(SmartHealthVitalInnerDetailsAdapter.OnItemClickListener { record ->
                }, labdetailsList, activity,post.normalvalues)
            holder.recyclerViewInner.itemAnimator = null;
        }


    }


    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val testName: TextView = itemView.findViewById(R.id.testName)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val testRange: TextView = itemView.findViewById(R.id.testRange)

        val recyclerViewInner: RecyclerView = itemView.findViewById(R.id.recyclerViewInner)


    }

}
