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
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import health.care.ai.proacdoc.R

import health.data.ai.proacdoc.api.models.smarthealthresponse.Data


import health.data.ai.proacdoc.application.MainApp


class SmartHealthAdapter(

    private val onItemClickListener: OnItemClickListener,
    private val mList: MutableList<Data>,
    private val activity: Activity
) :
    RecyclerView.Adapter<SmartHealthAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_smart_health_row, parent, false)

        return ViewHolder(view)
    }


    class OnItemClickListener(val clickListener: (meme: Data) -> Unit) {
        fun onClick(meme: Data) = clickListener(meme)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val post = mList[position]
holder.cardView.setOnClickListener(View.OnClickListener {
    onItemClickListener.onClick(post)
})
        holder.testName.text = post.data.get(0).normalizedText
        Glide.with(MainApp.instance)
            .load(post.data.get(0).image)

            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.img)

        for (vitaldetail in post.data) {

            var vdetail = vitaldetail.vitaldetails

            if (vitaldetail.medicalrecord.size != 0) {
                for (item in vitaldetail.medicalrecord) {
                    val array = vdetail.normalvalues.split("-")

                    if (array.size == 2) {
                        val min = array[0].replace(",", "")
                        val max = array[1].replace(",", "")
                        val newvalue = item.testvalue.replace(",", "").replace("^", "")

                        if (newvalue.toDouble() < min.toDouble()) {
                            //Low
                            mList[position].outofRange = mList[position].outofRange.inc()
                        } else
                            if (newvalue.toDouble() > max.toDouble()) {
                                mList[position].outofRange = mList[position].outofRange.inc()
                            } else {
                                if (newvalue.toDouble() > min.toDouble() && newvalue.toDouble() < max.toDouble()) {
                                    mList[position].notmalRange = mList[position].notmalRange.inc()
                                }

                            }


                    }
                }

            }























            if (vitaldetail.medicalrecord.size == 0) {
                holder.testN0Data.visibility = View.VISIBLE

            } else {
                break;
            }

        }

        if (post.outofRange != 0) {
            holder.img.setColorFilter(
                ContextCompat.getColor(
                    MainApp.instance,
                    R.color.red
                ), android.graphics.PorterDuff.Mode.SRC_ATOP
            )
            holder.testoutofRange.text = post.outofRange.toString() + " out of range"
            holder.testoutofRange.visibility = View.VISIBLE
        } else {
            if (post.notmalRange != 0) {
                holder.img.setColorFilter(
                    ContextCompat.getColor(
                        MainApp.instance,
                        R.color.green
                    ), android.graphics.PorterDuff.Mode.SRC_ATOP
                )

                holder.testinRange.text = post.notmalRange.toString() + " in range"
                holder.testinRange.visibility = View.VISIBLE
            }


        }

    }


    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {


        val testName: TextView = itemView.findViewById(R.id.testName)
        val img: ImageView = itemView.findViewById(R.id.img)
        val testN0Data: TextView = itemView.findViewById(R.id.testN0Data)
        val testoutofRange: TextView = itemView.findViewById(R.id.testoutofRange)
        val testinRange: TextView = itemView.findViewById(R.id.testinRange)
        val cardView: CardView = itemView.findViewById(R.id.cardView)


    }

}
