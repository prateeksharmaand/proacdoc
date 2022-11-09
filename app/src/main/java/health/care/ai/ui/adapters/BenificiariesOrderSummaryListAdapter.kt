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
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import health.care.ai.R
import health.care.ai.api.models.benificiary.get.Data
import health.care.ai.api.models.labordersummary.BenMaster


import health.care.ai.application.MainApp


class BenificiariesOrderSummaryListAdapter(

    private val onItemClickListener: OnItemClickListener,
    private val mList: MutableList<BenMaster>,
    private val activity: Activity
) :
    RecyclerView.Adapter<BenificiariesOrderSummaryListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_beni__orser_summary_row, parent, false)

        return ViewHolder(view)
    }


    class OnItemClickListener(val clickListener: (meme: BenMaster) -> Unit) {
        fun onClick(meme: BenMaster) = clickListener(meme)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val post = mList[position]

        holder.txtBenificiaries.text = post.name + " ("+ post.age + post.gender+")"
        holder.txtStatus.text = post.status
        when (post.status) {
            "CANCELLED" -> {

                holder.txtStatus.setTextColor(MainApp.instance.resources.getColor(R.color.red))
            }
            "DONE","ACCEPTED" -> {

                holder.txtStatus.setTextColor(MainApp.instance.resources.getColor(R.color.green))
            }
            else -> {
                holder.txtStatus.setTextColor(MainApp.instance.resources.getColor(R.color.unicorn_black))
            }
        }

        if (post.url.isNullOrBlank())
        {
            holder.btnViewReport.visibility=View.GONE
        }
        else
        {
            holder.btnViewReport.visibility=View.VISIBLE
            holder.btnViewReport.setOnClickListener(View.OnClickListener {
                onItemClickListener.onClick(post)
            })
        }



    }


    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {


        val txtBenificiaries: TextView = itemView.findViewById(R.id.txtBenificiaries)
        val txtStatus: TextView = itemView.findViewById(R.id.txtStatus)
        val btnViewReport: TextView = itemView.findViewById(R.id.btnViewReport)




    }

}
