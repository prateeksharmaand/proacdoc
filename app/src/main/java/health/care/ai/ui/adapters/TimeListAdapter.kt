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
import androidx.recyclerview.widget.RecyclerView
import health.care.ai.R
import health.care.ai.api.models.medicinetime.MedicineTimings


class TimeListAdapter(

    private val onItemClickListener: OnItemClickListener,
    private var mList: MutableList<MedicineTimings>,
    private val activity: Activity
) :
    RecyclerView.Adapter<TimeListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.medicinetimingsrow, parent, false)

        return ViewHolder(view)
    }

    fun updateData( mList: MutableList<MedicineTimings>) {
        this.mList = mList
        notifyDataSetChanged()
    }
    class OnItemClickListener(val clickListener: (meme: MedicineTimings) -> Unit) {
        fun onClick(meme: MedicineTimings) = clickListener(meme)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val post = mList[position]

        holder.txtTiming.text = post.timings
        holder.txtRemove.setOnClickListener(View.OnClickListener {

            onItemClickListener.onClick(post)
        })

    }


    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {


        val txtTiming: TextView = itemView.findViewById(R.id.txtTiming)
        val txtRemove: TextView = itemView.findViewById(R.id.txtRemove)


    }

}
