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

package health.care.ai.ui.referandearn

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.lifecycle.Observer
import com.google.android.play.core.review.ReviewManagerFactory
import health.care.ai.api.models.User.UserModel
import health.care.ai.api.models.customvitaldetails.AddCustomVitalsRequest
import health.care.ai.api.models.heartrate.AddHeartRateRequest
import health.care.ai.api.models.vitalsbymajorvitalid.Data
import health.care.ai.application.MainApp
import health.care.ai.databinding.ActivityAddCustomVitalRecordBinding
import health.care.ai.databinding.ActivityReferandearnBinding
import health.care.ai.ui.adapters.CustomDropDownAdapter
import health.care.ai.ui.labvitalstrend.LabVitalsViewModel
import health.care.ai.ui.login.LoginViewModel
import health.care.ai.utils.Utils.CompanionClass.Companion.setAppLocale
import io.branch.indexing.BranchUniversalObject
import io.branch.referral.Branch
import io.branch.referral.util.LinkProperties
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class ReferandEarnActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReferandearnBinding
    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var user: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReferandearnBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initObserver()
        loginViewModel.checkLoggedInUserFlow();
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
        binding.btnInvite.setOnClickListener(View.OnClickListener {

            ShareCompat.IntentBuilder(this)
                .setType("text/plain")
                .setChooserTitle("Generate your Ayushman Bharat ABHA Card ")
                .setText("Earn Free Paytm Cash Download And Refer To our Friend  " + binding.txtReferal.text)
                .startChooser();
        })




    }


    fun initObserver() {
        loginViewModel.userDetails.observe(this, Observer {
            user = it
            val buo = BranchUniversalObject()
            val lp = LinkProperties()

                .setCampaign(user.UserId.toString())
                .setStage("sign up")

            buo.generateShortUrl(
                MainApp.instance, lp,
                Branch.BranchLinkCreateListener { url, error ->
                    if (error == null) {
                      binding.txtReferal.text=url

                    }
                })
        })




    }

}