/*************************************************
 * Created by Efendi Hariyadi on 10/08/22, 11:22 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 10/08/22, 11:22 AM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 10/08/22, 11:22 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 10/08/22, 11:22 AM
 ************************************************/

package health.care.ai.ui.privacy

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.play.core.review.ReviewManagerFactory
import health.care.ai.api.models.User.UserModel
import health.care.ai.api.models.customvitaldetails.AddCustomVitalsRequest
import health.care.ai.api.models.heartrate.AddHeartRateRequest
import health.care.ai.api.models.vitalsbymajorvitalid.Data
import health.care.ai.databinding.ActivityAbhaBinding
import health.care.ai.databinding.ActivityAddCustomVitalRecordBinding
import health.care.ai.databinding.ActivityPrivacypolicyBinding
import health.care.ai.ui.adapters.CustomDropDownAdapter
import health.care.ai.ui.labvitalstrend.LabVitalsViewModel
import health.care.ai.ui.login.LoginViewModel
import health.care.ai.utils.Utils.CompanionClass.Companion.setAppLocale
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class PrivacyPolicyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrivacypolicyBinding
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivacypolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        try {
            val manager = ReviewManagerFactory.create(applicationContext)
            val request = manager.requestReviewFlow()
            request.addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val reviewInfo = task.result
                    val flow = manager.launchReviewFlow(this, reviewInfo)
                    flow.addOnCompleteListener { _ ->

                    }

                } else {

                }
            }
        } catch (e: Exception) {
        }

    }

    fun initUI() {


        setSupportActionBar(binding.toolbar)


        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener(View.OnClickListener {

            onBackPressed()

        })
        webView = binding.webview
        webView.settings.setJavaScriptEnabled(true)
        webView.getSettings().setDomStorageEnabled(true);
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }
        }
        webView.loadUrl("https://pages.flycricket.io/aayussmaan-bhaart-aabhaa-p/privacy.html")






    }


}