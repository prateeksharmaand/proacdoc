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
import android.graphics.Paint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import health.care.ai.R
import health.care.ai.api.models.labtest.login.ProfileTest.Data
import health.care.ai.application.MainApp
import health.care.ai.repository.PostsRoomRepository
import health.care.ai.utils.HomeProfileTestsDiffCallback
import kotlin.math.roundToInt


class HomeProfileTestsAdapter(

    private val onItemClickListener: OnItemClickListener,
    private val mList: MutableList<Data>,
    private val activity: Activity,

) :
    RecyclerView.Adapter<HomeProfileTestsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_lab_test__row, parent, false)

        return ViewHolder(view)
    }


    class OnItemClickListener(val clickListener: (meme: Data) -> Unit) {
        fun onClick(meme: Data) = clickListener(meme)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val post = mList[position]

        holder.testName.text = post.name
        holder.testCount.text = post.testCount + " Tests"
        holder.testDiscuntedrate.text =
            MainApp.instance.resources.getString(R.string.rs, post.rate)
       // holder.testRate.text = MainApp.instance.resources.getString(R.string.rs, post.rate)
      //  holder.testRate.setPaintFlags(holder.testRate.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)


      //  var percentage = (post.discount.toDouble() / post.rate.toDouble()) * 100

     //   holder.testDiscount.text = percentage.roundToInt().toString() + "% discount"


        holder.cardView.setOnClickListener {

            onItemClickListener.onClick(post)
        }


    }


    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val testName: TextView = itemView.findViewById(R.id.testName)
        val testCount: TextView = itemView.findViewById(R.id.testCount)
        val cardView: CardView = itemView.findViewById(R.id.cardView)

        val testDiscuntedrate: TextView = itemView.findViewById(R.id.testDiscuntedrate)
        val testRate: TextView = itemView.findViewById(R.id.testRate)
        val testDiscount: TextView = itemView.findViewById(R.id.testDiscount)


    }

    fun updateEmployeeListItems(employees: MutableList<Data>) {
        val diffCallback = HomeProfileTestsDiffCallback(this.mList, employees!!)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        mList.clear()
        mList.addAll(employees)
        diffResult.dispatchUpdatesTo(this)
    }
}
