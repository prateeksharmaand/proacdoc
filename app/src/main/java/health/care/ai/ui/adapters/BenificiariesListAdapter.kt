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


import health.care.ai.application.MainApp


class BenificiariesListAdapter(

    private val onItemClickListener: OnItemClickListener,
    private val mList: MutableList<Data>,
    private val activity: Activity
) :
    RecyclerView.Adapter<BenificiariesListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_beni_row, parent, false)

        return ViewHolder(view)
    }


    class OnItemClickListener(val clickListener: (meme: Data) -> Unit) {
        fun onClick(meme: Data) = clickListener(meme)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val post = mList[position]
        holder.chkBebi.tag = position
        holder.chkBebi.text = post.beniname+" ("+post.gender+" "+post.age+"Y)"
        holder.chkBebi.setOnCheckedChangeListener { buttonView, isChecked ->
            try {
                val pos = holder.chkBebi.tag as Int
                mList[pos].isSelected = !mList[pos].isSelected
                onItemClickListener.onClick(post)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }


    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {


        val chkBebi: CheckBox = itemView.findViewById(R.id.chkBebi)


    }

}
