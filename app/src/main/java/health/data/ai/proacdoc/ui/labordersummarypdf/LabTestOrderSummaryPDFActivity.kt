package health.data.ai.proacdoc.ui.labordersummarypdf


import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView
import health.care.ai.proacdoc.databinding.ActivityLabOrderSummaryPdfBinding
import health.data.ai.proacdoc.application.MainApp

import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class LabTestOrderSummaryPDFActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLabOrderSummaryPdfBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLabOrderSummaryPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)



        initUI()
        initObserver()



    }


    fun initUI() {

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener(View.OnClickListener { onBackPressed() })

    }


    fun initObserver() {
        RetrivePDFFromURL(binding.pdfView).execute( MainApp.pdfUrl)

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