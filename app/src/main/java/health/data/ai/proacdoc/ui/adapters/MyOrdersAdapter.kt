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
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import health.care.ai.proacdoc.R

import health.data.ai.proacdoc.application.MainApp


class MyOrdersAdapter(

    private val onItemClickListener: OnItemClickListener,
    private val mList: MutableList<health.data.ai.proacdoc.api.models.mylaborders.Data>,
    private val activity: Activity,

    ) :
    RecyclerView.Adapter<MyOrdersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_my_lab__order_row, parent, false)

        return ViewHolder(view)
    }


    class OnItemClickListener(val clickListener: (meme: health.data.ai.proacdoc.api.models.mylaborders.Data) -> Unit) {
        fun onClick(meme: health.data.ai.proacdoc.api.models.mylaborders.Data) = clickListener(meme)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val post = mList[position]

       holder.txtOrder.text=post.orderId
        holder.txtName.text=post.product
        holder.txtApptDetails.text=post.appointmentDate
        holder.txtBooked.text=post.bookedOn



        holder.txtAmount.text= MainApp.instance.resources.getString(R.string.rs,post.rate.toString())
        holder.btnViewDetail.setOnClickListener(View.OnClickListener {

            onItemClickListener.onClick(post)
        })
        holder.cardView.setOnClickListener(View.OnClickListener {

            onItemClickListener.onClick(post)
        })




    }


    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val txtOrder: TextView = itemView.findViewById(R.id.txtOrder)
        val txtAmount: TextView = itemView.findViewById(R.id.txtAmount)
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtApptDetails: TextView = itemView.findViewById(R.id.txtApptDetails)
        val btnViewDetail: Button = itemView.findViewById(R.id.btnViewDetail)
        val txtBooked: TextView = itemView.findViewById(R.id.txtBooked)
        val cardView: CardView = itemView.findViewById(R.id.cardView)










    }


}
