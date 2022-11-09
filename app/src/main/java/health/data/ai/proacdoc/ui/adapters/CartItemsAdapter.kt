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
import health.care.ai.proacdoc.R

import health.data.ai.proacdoc.application.MainApp
import health.data.ai.proacdoc.room.entity.Cart


class CartItemsAdapter(

    private val onItemClickListener: OnItemClickListener,
    private val mList: MutableList<Cart>,
    private val activity: Activity,

    ) :
    RecyclerView.Adapter<CartItemsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_lab_cart__row, parent, false)

        return ViewHolder(view)
    }


    class OnItemClickListener(val clickListener: (meme: Cart) -> Unit) {
        fun onClick(meme: Cart) = clickListener(meme)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val post = mList[position]

        holder.testName.text = post.Name
        holder.imageDelete.setOnClickListener {

            onItemClickListener.onClick(post)
        }
        holder.testDiscuntedrate.text =
            MainApp.instance.resources.getString(R.string.rs, post.Rate)

        holder.testNameInner.text = post.TestList

    }


    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val testName: TextView = itemView.findViewById(R.id.testName)
        val imageDelete: ImageView = itemView.findViewById(R.id.imageDelete)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val testDiscuntedrate: TextView = itemView.findViewById(R.id.testDiscuntedrate)
        val testNameInner: TextView = itemView.findViewById(R.id.testNameInner)

    }


}
