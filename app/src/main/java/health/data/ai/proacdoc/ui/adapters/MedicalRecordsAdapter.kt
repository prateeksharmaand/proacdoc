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
import android.os.AsyncTask
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.barteksc.pdfviewer.PDFView
import health.care.ai.proacdoc.R

import health.data.ai.proacdoc.api.models.medicalreport.getreportspdf.Data
import health.data.ai.proacdoc.application.MainApp


import health.data.ai.proacdoc.utils.MedicalReportsDiffCallback

import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class MedicalRecordsAdapter(

    private val onItemClickListener: OnItemClickListener,
    private val mList: MutableList<Data>,
    private val activity: Activity
) :
    RecyclerView.Adapter<MedicalRecordsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_posts_row, parent, false)

        return ViewHolder(view)
    }


    class OnItemClickListener(val clickListener: (meme: Data) -> Unit) {
        fun onClick(meme: Data) = clickListener(meme)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val post = mList[position]

        holder.txtDateTime.text = MainApp.instance.getString(R.string.postedon) + post.ago


        if (post.fileType == 1) {
            holder.pdfView.visibility = View.VISIBLE
            RetrivePDFFromURL(holder.pdfView).execute(post.fileUrl)
        } else {
            holder.imgReport.visibility = View.VISIBLE
            Glide.with(MainApp.instance)
                .load(post.fileUrl)

                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imgReport)
        }




        holder.pdfView.setOnClickListener {

            onItemClickListener.onClick(post)
        }
        holder.imgReport.setOnClickListener {

            onItemClickListener.onClick(post)
        }





        holder.itemView.setOnClickListener {

            onItemClickListener.onClick(post)
        }

        when(post.smartReport) {
            0 -> {

            }
            1 -> {
                holder.llSmartReport.visibility=View.VISIBLE

            }
            2 -> {

            }


        }

    }

    class RetrivePDFFromURL(pdfView: PDFView) : AsyncTask<String, Void, InputStream>() {

        val mypdfView: PDFView = pdfView
        override fun doInBackground(vararg params: String?): InputStream? {

            var inputStream: InputStream? = null
            try {

                val url = URL(params.get(0))

                val urlConnection: HttpURLConnection = url.openConnection() as HttpsURLConnection


                if (urlConnection.responseCode == 200) {

                    inputStream = BufferedInputStream(urlConnection.inputStream)
                }
            }
            // on below line we are adding catch block to handle exception
            catch (e: Exception) {
                // on below line we are simply printing
                // our exception and returning null
                e.printStackTrace()
                return null;
            }
            // on below line we are returning input stream.
            return inputStream;
        }

        // on below line we are calling on post execute
        // method to load the url in our pdf view.
        override fun onPostExecute(result: InputStream?) {
            // on below line we are loading url within our
            // pdf view on below line using input stream.
            mypdfView.fromStream(result).load()


        }
    }


    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val pdfView: PDFView = itemView.findViewById(R.id.pdfView)
        val imgReport: ImageView = itemView.findViewById(R.id.imgReport)
        val llSmartReport: LinearLayout = itemView.findViewById(R.id.llSmartReport)

        val txtDateTime: TextView = itemView.findViewById(R.id.txtDateTime)


    }

    fun updateEmployeeListItems(employees: MutableList<Data>) {
        val diffCallback = MedicalReportsDiffCallback(this.mList, employees!!)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        mList.clear()
        mList.addAll(employees)
        diffResult.dispatchUpdatesTo(this)
    }
}
