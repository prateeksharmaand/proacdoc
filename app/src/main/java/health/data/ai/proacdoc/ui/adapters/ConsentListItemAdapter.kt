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
import androidx.recyclerview.widget.RecyclerView
import health.care.ai.proacdoc.R
import health.data.ai.proacdoc.api.models.abhaconsentdetails.LocalConsentRequest


class ConsentListItemAdapter(

    private val onItemClickListener: OnItemClickListener,
    private val mList: MutableList<LocalConsentRequest>,
    private val activity: Activity
) :
    RecyclerView.Adapter<ConsentListItemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.consent_list_row, parent, false)

        return ViewHolder(view)
    }


    class OnItemClickListener(val clickListener: (meme: LocalConsentRequest) -> Unit) {
        fun onClick(meme: LocalConsentRequest) = clickListener(meme)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            val post = mList[position]
            holder.txtrequestername.text = post.Providername

            holder.txtRequestType.text = post.RequestType
            holder.txtPerpose.text = post.Perpose

            holder.txtPeriod.text = post.Period

            holder.txtStatus.text = post.Status

        } catch (e: Exception) {
        }

    }


    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {


        val txtrequestername: TextView = itemView.findViewById(R.id.txtrequestername)
        val txtRequestType: TextView = itemView.findViewById(R.id.txtRequestType)
        val txtPerpose: TextView = itemView.findViewById(R.id.txtPerpose)
        val txtPeriod: TextView = itemView.findViewById(R.id.txtPeriod)

        val txtStatus: TextView = itemView.findViewById(R.id.txtStatus)
    }

}
