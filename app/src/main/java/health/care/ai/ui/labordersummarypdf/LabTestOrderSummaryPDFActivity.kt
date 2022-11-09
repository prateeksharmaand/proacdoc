package health.care.ai.ui.labordersummarypdf


import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.barteksc.pdfviewer.PDFView
import health.care.ai.R
import health.care.ai.api.models.User.UserModel
import health.care.ai.api.models.labordersummary.LabOrderSummaryrequest
import health.care.ai.application.MainApp
import health.care.ai.databinding.ActivityLabOrderSummaryBinding
import health.care.ai.databinding.ActivityLabOrderSummaryPdfBinding
import health.care.ai.room.entity.Cart
import health.care.ai.ui.adapters.AppointmentsSlotSpinnerAdapter
import health.care.ai.ui.adapters.BenificiariesOrderSummaryListAdapter
import health.care.ai.ui.homefragment.LabTestViewModel
import health.care.ai.ui.login.LoginViewModel
import health.care.ai.ui.reportdetails.medicalpdf.MedicalRecordsPDFFragment
import health.care.ai.utils.DataStoreManager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
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