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

package health.care.ai.ui.smarthealthdetails

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import health.care.ai.R
import health.care.ai.api.models.User.UserModel
import health.care.ai.api.models.heartrate.AddHeartRateRequest
import health.care.ai.api.models.smarthealthresponse.Data
import health.care.ai.api.models.smarthealthresponse.Vitaldetails

import health.care.ai.databinding.ActivityHeartRateDetailsBinding
import health.care.ai.databinding.ActivitySmarthealthdetailsBinding
import health.care.ai.ui.adapters.SmartHealthAdapter
import health.care.ai.ui.adapters.SmartHealthVitalDetailsAdapter
import health.care.ai.ui.heartratemonitor.HeartRateMonitorActivity
import health.care.ai.ui.labvitalstrend.LabVitalsViewModel
import health.care.ai.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SmartHealthDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySmarthealthdetailsBinding
    private val vitalsViewModel: LabVitalsViewModel by viewModel()
    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var user: UserModel
    private lateinit var data: Data

    private lateinit var postAdapter: SmartHealthVitalDetailsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySmarthealthdetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        data = intent.extras!!.get("record") as Data
        initUI()
        initObserver()
        loginViewModel.checkLoggedInUserFlow();
    }

    fun initUI() {
        setSupportActionBar(binding.toolbar)
        data.data[0].normalizedText.let {
            binding.toolbar.title = it
            val vitaldetailsList = ArrayList<Vitaldetails>().toMutableList()
            var count=0;
            for (vitaldetail in data.data) {
                vitaldetail.vitaldetails.medicalrecord=data.data[count].medicalrecord


                vitaldetailsList.add(vitaldetail.vitaldetails)
                count= count.inc()
                Log.e("count",count.toString())
            }
            linearLayoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            binding.recyclerView.layoutManager = linearLayoutManager
            postAdapter =
                SmartHealthVitalDetailsAdapter(SmartHealthVitalDetailsAdapter.OnItemClickListener { record ->
                }, vitaldetailsList, this)
            binding.recyclerView.adapter = postAdapter
            binding.recyclerView.itemAnimator = null;


        }


        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener(View.OnClickListener { onBackPressed() })
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