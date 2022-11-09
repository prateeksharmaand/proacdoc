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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import health.care.ai.proacdoc.R

import health.data.ai.proacdoc.api.models.benificiary.get.Data


import health.data.ai.proacdoc.application.MainApp


class ProfilesListAdapter(

    private val onItemClickListener: OnItemClickListener,
    private val mList: MutableList<Data>,
    private val activity: Activity
) :
    RecyclerView.Adapter<ProfilesListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.profile_list_row, parent, false)

        return ViewHolder(view)
    }


    class OnItemClickListener(val clickListener: (meme: Data) -> Unit) {
        fun onClick(meme: Data) = clickListener(meme)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val post = mList[position]
        holder.txtAbhaid.text = post.abhanumber
        holder.chkBebi.text = post.beniname
        holder.cardView.setOnClickListener(View.OnClickListener {

            onItemClickListener.onClick(post)
        })
        try {


            Glide.with(MainApp.instance)

                .load(MainApp.instance.resources.getDrawable(R.drawable.ic_user))
                .circleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.userImage)
        } catch (e: Exception) {
        }
    }


    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {


        val chkBebi: TextView = itemView.findViewById(R.id.name)
        val userImage: ImageView = itemView.findViewById(R.id.userImage)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val txtAbhaid: TextView = itemView.findViewById(R.id.txtAbhaid)


    }

}
