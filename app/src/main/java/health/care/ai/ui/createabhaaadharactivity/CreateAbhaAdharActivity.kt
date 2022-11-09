/*************************************************
 * Created by Efendi Hariyadi on 11/10/22, 1:35 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 11/10/22, 12:28 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 11/10/22, 12:27 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 11/10/22, 12:26 PM
 ************************************************/

package health.care.ai.ui.createabhaaadharactivity

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.*
import android.provider.Settings
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import health.care.ai.R
import health.care.ai.api.models.labtest.login.ProfileTest.Data
import health.care.ai.api.models.labtest.login.ProfileTest.ProfileTestRequest
import health.care.ai.api.models.medicinetime.MedicineTimings
import health.care.ai.databinding.ActivityAbhaCreateAdhaarBinding
import health.care.ai.databinding.ActivityAbhaHomeBinding
import health.care.ai.databinding.ActivityAddmedicationBinding
import health.care.ai.room.entity.MedicineEntity
import health.care.ai.room.entity.MedicineTimingsEntity
import health.care.ai.ui.adapters.TimeListAdapter
import health.care.ai.ui.login.LoginViewModel
import health.care.ai.ui.medication.MedicineViewModel
import health.care.ai.utils.MedicineService
import health.care.ai.utils.Utils
import androidx.lifecycle.Observer
import health.care.ai.api.models.customvitaldetails.AddCustomVitalsRequest
import health.care.ai.api.models.registerabhagenerateadharotp.RegisterAbhaGenerateAdharOtpRequest
import health.care.ai.api.models.verifyreisteraadharotp.VerifyRegisterAAadharOtpRequest
import health.care.ai.application.MainApp
import health.care.ai.ui.createabhamobileactivity.CreateAbhaMobileActivity
import health.care.ai.ui.reportdetails.ReportDetails
import health.care.ai.utils.DataStoreManager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.teamgravity.imageradiobutton.GravityImageRadioButton
import java.text.SimpleDateFormat
import java.util.*


class CreateAbhaAdharActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAbhaCreateAdhaarBinding
    private val loginViewModel: LoginViewModel by viewModel()
    private val abhaViewModel: AbhaViewModel by viewModel()
    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var abhaToken: String
    private lateinit var txnId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAbhaCreateAdhaarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        dataStoreManager = DataStoreManager(this)
        lifecycleScope.launch {
            dataStoreManager.getAbhaToken.collect { counter ->
                abhaToken = counter


            }
        }
        loginViewModel.getAbhaToken()
        initObserver()
    }

    fun initUI() {


        setSupportActionBar(binding.toolbar)


        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener(View.OnClickListener {

            onBackPressed()

        })
        binding.btnSendOtp.setOnClickListener(View.OnClickListener {

            if (binding.etAdhar.text.toString().length == 12) {

                val registerAbhaGenerateAdharOtpRequest = RegisterAbhaGenerateAdharOtpRequest()
                registerAbhaGenerateAdharOtpRequest.aadhaar = binding.etAdhar.text.toString()
                abhaViewModel.abhaRequestAdharOtp(
                    registerAbhaGenerateAdharOtpRequest,
                    "Bearer $abhaToken"
                )
            } else {
                Toast.makeText(applicationContext, "Enter Valid Aadhar Number", Toast.LENGTH_SHORT)

                    .show()


            }


        })

        binding.btnVerifyOtp.setOnClickListener(View.OnClickListener {

            if (binding.etOtp.otp.toString().length == 6) {

                val verifyRegisterAAadharOtpRequest = VerifyRegisterAAadharOtpRequest()
                verifyRegisterAAadharOtpRequest.txnId = txnId
                verifyRegisterAAadharOtpRequest.otp = binding.etOtp.otp
                abhaViewModel.verifyabhaRequestAdharOtp(
                    verifyRegisterAAadharOtpRequest,
                    "Bearer $abhaToken"
                )
            } else {
                Toast.makeText(applicationContext, "Enter Valid Aadhar Number", Toast.LENGTH_SHORT)

                    .show()


            }


        })


    }

    fun initObserver() {


        loginViewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
        abhaViewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
        loginViewModel.abhatoken.observe(this, Observer {
            lifecycleScope.launch {
                dataStoreManager.setAbhaToken(it.results.data.accessToken)


            }
        })
        abhaViewModel.abhaRequestAdharOtpResponse.observe(this, Observer {
            if(it!=null)
            {
                txnId = it.txnId
                binding.progressBar.visibility = View.GONE
                binding.llMobileNo.visibility = View.GONE
                binding.btnSendOtp.visibility = View.GONE
                binding.llOtp.visibility = View.VISIBLE
                binding.etOtp.requestFocusOTP()

            }
            else
            {
                Toast.makeText(
                    applicationContext,
                    "Something went wrong, Try again later",
                    Toast.LENGTH_SHORT
                ).show()

            }

        })
        abhaViewModel.verifyAbhaRegisterAadharOtpResponse.observe(this, Observer {
            txnId = it.txnId
            lifecycleScope.launch {
                dataStoreManager.setTxnId(txnId)


            }
            Toast.makeText(
                applicationContext,
                "Verification Successful, Please Verify Mobile No ",
                Toast.LENGTH_SHORT
            ).show()


            val intent = Intent(this, CreateAbhaMobileActivity::class.java)
            startActivity(intent)
            finish()
        })

    }


}