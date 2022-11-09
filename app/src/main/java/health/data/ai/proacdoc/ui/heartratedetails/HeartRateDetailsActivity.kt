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

package health.data.ai.proacdoc.ui.heartratedetails

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import health.care.ai.proacdoc.BuildConfig

import health.care.ai.proacdoc.R
import health.care.ai.proacdoc.databinding.ActivityHeartRateDetailsBinding

import health.data.ai.proacdoc.api.models.User.UserModel
import health.data.ai.proacdoc.api.models.heartrate.AddHeartRateRequest

import health.data.ai.proacdoc.ui.heartratemonitor.HeartRateMonitorActivity
import health.data.ai.proacdoc.ui.labvitalstrend.LabVitalsViewModel
import health.data.ai.proacdoc.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class HeartRateDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHeartRateDetailsBinding
    private val vitalsViewModel: LabVitalsViewModel by viewModel()
    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var user: UserModel
    val record = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeartRateDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initObserver()
        loginViewModel.checkLoggedInUserFlow();
    }

    fun initUI() {
        val record:Int = intent.getIntExtra("record",0)

        binding.txthrt.text=record.toString()
        setSupportActionBar(binding.toolbar)
        binding.btnEdit.setOnClickListener(View.OnClickListener {

            val intent = Intent(this, HeartRateMonitorActivity::class.java)

            startActivity(intent)
            finish()
        })
        binding.btnAdd.setOnClickListener(View.OnClickListener {
            val addHeartRateRequest = AddHeartRateRequest()
            addHeartRateRequest.UserId = user.UserId
            addHeartRateRequest.testvalue=record
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val currentDate = sdf.format(Date())

            addHeartRateRequest.dated = currentDate
            vitalsViewModel.addHeartBeatVital(addHeartRateRequest)

        })
        binding.cardShare.setOnClickListener(View.OnClickListener {

            val appPackageName = BuildConfig.APPLICATION_ID

            val shareBodyText = "https://play.google.com/store/apps/details?id=$appPackageName"

            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TITLE, "Monitor Heart Rates")
                putExtra(Intent.EXTRA_TEXT, shareBodyText)
            }
            startActivity(Intent.createChooser(sendIntent, null))
        })
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener(View.OnClickListener { onBackPressed() })
        when (record) {
            in 31..59 -> {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    binding.txthrtState.text =Html.fromHtml( resources.getString(
                        R.string.heartRateDetailsred, getString(
                                            R.string.low)), HtmlCompat.FROM_HTML_MODE_LEGACY)
                }
                else{

                    binding.txthrtState.text =Html.fromHtml( resources.getString(R.string.heartRateDetailsred, getString(
                        R.string.low)))
                }

            }
            in 60..99 -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    binding.txthrtState.text =Html.fromHtml( resources.getString(R.string.heartRateDetailsgreen, getString(
                                            R.string.halthy)), HtmlCompat.FROM_HTML_MODE_LEGACY)
                }
                else{

                    binding.txthrtState.text =Html.fromHtml( resources.getString(R.string.heartRateDetailsgreen,  getString(
                        R.string.halthy)))
                }

            }
            in 100..139 -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    binding.txthrtState.text =Html.fromHtml( resources.getString(R.string.heartRateDetailsred, getString(
                                            R.string.high)), HtmlCompat.FROM_HTML_MODE_LEGACY)
                }
                else{

                    binding.txthrtState.text =Html.fromHtml( resources.getString(R.string.heartRateDetailsred, getString(
                        R.string.high)))
                }

            }
            in 140..220 -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    binding.txthrtState.text =Html.fromHtml( resources.getString(R.string.heartRateDetailsred, getString(
                                            R.string.danger)), HtmlCompat.FROM_HTML_MODE_LEGACY)
                }
                else{

                    binding.txthrtState.text =Html.fromHtml( resources.getString(R.string.heartRateDetailsred, getString(
                        R.string.danger)))
                }

            }
        }

    }

    fun initObserver() {
        loginViewModel.userDetails.observe(this, Observer {
            user = it
        })

        vitalsViewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
        vitalsViewModel.addHeartRateresponse.observe(this, Observer {
            it?.let { list ->
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                finish()
            }
        })





    }



}