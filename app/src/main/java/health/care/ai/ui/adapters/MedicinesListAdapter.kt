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


import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Paint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import health.care.ai.R
import health.care.ai.api.models.labtest.login.ProfileTest.Data
import health.care.ai.application.MainApp
import health.care.ai.repository.PostsRoomRepository
import health.care.ai.room.entity.MedicineEntity
import health.care.ai.utils.HomeProfileTestsDiffCallback
import health.care.ai.utils.MedicineListDiffCallback
import health.care.ai.utils.Utils.CompanionClass.Companion.ifNonNull
import kotlin.math.roundToInt


class MedicinesListAdapter(

    private val onItemClickListener: OnItemClickListener,
    private val mList: MutableList<MedicineEntity>,
    private val activity: Activity,

    ) :
    RecyclerView.Adapter<MedicinesListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerviewmedicine, parent, false)

        return ViewHolder(view)
    }


    class OnItemClickListener(val clickListener: (meme: MedicineEntity) -> Unit) {
        fun onClick(meme: MedicineEntity) = clickListener(meme)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val post = mList[position]

        holder.txtMedicineName.text = post.medicineName
        holder.medicineDetails.text =
            post.MedicineType + " " + post.Strength + " " + post.StrengthUnit

        post.Status.ifNonNull {
            holder.txtMedicineStatus.visibility=View.VISIBLE
            if (it) {
                holder.txtMedicineStatus.text = "Medicine Status: Taken\nLast Reminder on " + post.LastUpdatedDate
            } else {
                holder.txtMedicineStatus.text = "Medicine Status: Skipped\nLast Reminder on " + post.LastUpdatedDate
            }


        }
        holder.txtMedicineName.text = post.medicineName

        when (post.Shape) {
            1 -> {

                Glide.with(MainApp.instance)
                    .load(R.drawable.stwo)

                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.imgMedicineIcon)


            }
            2 -> {
                Glide.with(MainApp.instance)
                    .load(R.drawable.sthree)

                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.imgMedicineIcon)
            }
            3 -> {
                Glide.with(MainApp.instance)
                    .load(R.drawable.sfour)

                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.imgMedicineIcon)
            }
            4 -> {
                Glide.with(MainApp.instance)
                    .load(R.drawable.sfive)

                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.imgMedicineIcon)
            }
            5 -> {
                Glide.with(MainApp.instance)
                    .load(R.drawable.ssix)

                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.imgMedicineIcon)
            }
        }


        /* holder.cardView.setOnClickListener {

             onItemClickListener.onClick(post)
         }
 */

    }


    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val txtMedicineName: TextView = itemView.findViewById(R.id.txtMedicineName)
        val txtMedicineStatus: TextView = itemView.findViewById(R.id.txtMedicineStatus)


        val medicineDetails: TextView = itemView.findViewById(R.id.medicineDetails)
        val imgMedicineIcon: ImageView = itemView.findViewById(R.id.imgMedicineIcon)
    }

    fun updateEmployeeListItems(employees: MutableList<MedicineEntity>) {
        val diffCallback = MedicineListDiffCallback(this.mList, employees!!)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        mList.clear()
        mList.addAll(employees)
        diffResult.dispatchUpdatesTo(this)
    }
}
