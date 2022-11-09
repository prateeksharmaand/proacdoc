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

package health.data.ai.proacdoc.ui.addcustomvitalrecord

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.play.core.review.ReviewManagerFactory
import health.care.ai.proacdoc.databinding.ActivityAddCustomVitalRecordBinding
import health.data.ai.proacdoc.api.models.User.UserModel
import health.data.ai.proacdoc.api.models.customvitaldetails.AddCustomVitalsRequest
import health.data.ai.proacdoc.api.models.vitalsbymajorvitalid.Data

import health.data.ai.proacdoc.ui.adapters.CustomDropDownAdapter
import health.data.ai.proacdoc.ui.labvitalstrend.LabVitalsViewModel
import health.data.ai.proacdoc.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class AddCustomVitalRecordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddCustomVitalRecordBinding
    private val vitalsViewModel: LabVitalsViewModel by viewModel()
    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var user: UserModel
    private lateinit var data: Data
    var majorvitalId: Int = 0
    lateinit var CustomDropDownAdapter: CustomDropDownAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCustomVitalRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        majorvitalId = intent.getIntExtra("majorvitalId", 0)
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

        binding.spinnerVitalName.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                data = CustomDropDownAdapter.getItem(position) as Data
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        binding.btnAddVitals.setOnClickListener(View.OnClickListener {

            if (binding.ettestValue.text.length == 0) {
                Toast.makeText(applicationContext, "Enter test Value", Toast.LENGTH_LONG).show()
                return@OnClickListener

            } else
                if (binding.ettestunit.text.length == 0) {
                    Toast.makeText(applicationContext, "Enter test Unit", Toast.LENGTH_LONG).show()
                    return@OnClickListener

                } else {
                    val addCustomVitalsRequest = AddCustomVitalsRequest()
                    addCustomVitalsRequest.UserId = user.UserId
                    addCustomVitalsRequest.testname = data.normalizedText

                    addCustomVitalsRequest.testvalue = binding.ettestValue.text.toString().toInt()

                    addCustomVitalsRequest.testunit = binding.ettestunit.text.toString()

                    addCustomVitalsRequest.normalizedText = data.normalizedText

                    addCustomVitalsRequest.vitalId = data.vitalId
                    addCustomVitalsRequest.majorVitalId = majorvitalId

                    val sdf = SimpleDateFormat("dd/MM/yyyy")
                    val currentDate = sdf.format(Date())

                    addCustomVitalsRequest.dated = currentDate
                    vitalsViewModel.addCustomVitals(addCustomVitalsRequest)
                }


        })


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
        vitalsViewModel.addCustomVitalResponse.observe(this, Observer {
            it?.let { list ->
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                finish()
            }
        })


        vitalsViewModel.VitalDetailsByIdResponse.observe(this, Observer {
            binding.progressBar.visibility = View.GONE
            CustomDropDownAdapter = CustomDropDownAdapter(this, it.results.data)
            binding.spinnerVitalName.adapter = CustomDropDownAdapter

        })
        vitalsViewModel.getVitalsByitalid(majorvitalId)


    }


}