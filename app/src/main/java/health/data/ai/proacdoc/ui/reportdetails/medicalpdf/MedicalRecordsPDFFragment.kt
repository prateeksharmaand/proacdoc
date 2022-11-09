/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 05/07/22, 1:23 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 05/07/22, 1:23 PM
 ************************************************/

package health.data.ai.proacdoc.ui.reportdetails.medicalpdf


import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.barteksc.pdfviewer.PDFView
import health.care.ai.proacdoc.databinding.FragmentMedicalRecordsPdfBinding
import health.data.ai.proacdoc.application.MainApp

import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class MedicalRecordsPDFFragment : Fragment() {
    private lateinit var binding: FragmentMedicalRecordsPdfBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMedicalRecordsPdfBinding.inflate(inflater, container, false)



        if (MainApp.fileType == 1) {
            binding.pdfView.visibility = View.VISIBLE
            RetrivePDFFromURL(binding.pdfView).execute( MainApp.pdfUrl)
        } else {
            binding.imgReport.visibility = View.VISIBLE
            Glide.with(MainApp.instance)
                .load(MainApp.pdfUrl)

                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imgReport)
        }
        return binding.root

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

}

