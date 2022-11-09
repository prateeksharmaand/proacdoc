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

package health.data.ai.proacdoc.ui.createabhamobileactivity

import android.content.Intent
import android.os.*
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import health.data.ai.proacdoc.ui.login.LoginViewModel
import androidx.lifecycle.Observer
import health.care.ai.proacdoc.databinding.ActivityAbhaCreateMobileBinding
import health.data.ai.proacdoc.api.models.registerabhageneratemobileotp.RegisterAbhaGenerateMobileOtp
import health.data.ai.proacdoc.api.models.registerabhaverifymobileotp.RegisterabhaVerifyMobileOtpRequest

import health.data.ai.proacdoc.ui.createabhaaadharactivity.AbhaViewModel
import health.data.ai.proacdoc.ui.registerabhaactivity.CreateAbhaActivity
import health.data.ai.proacdoc.utils.DataStoreManager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class CreateAbhaMobileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAbhaCreateMobileBinding
    private val loginViewModel: LoginViewModel by viewModel()
    private val abhaViewModel: AbhaViewModel by viewModel()
    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var abhaToken: String
    private lateinit var txnId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAbhaCreateMobileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        dataStoreManager = DataStoreManager(this)
        lifecycleScope.launch {
            dataStoreManager.getAbhaToken.collect { counter ->
                abhaToken = counter


            }
            dataStoreManager.getTxnId.collect { counter ->
                txnId = counter


            }
        }
        lifecycleScope.launch {

            dataStoreManager.getTxnId.collect { counter ->
                txnId = counter
                Log.e("txnId", txnId)

            }
        }

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

            if (binding.etAdhar.text.toString().length == 10) {

                val registerAbhaGenerateMobileOtp = RegisterAbhaGenerateMobileOtp()
                registerAbhaGenerateMobileOtp.mobile = binding.etAdhar.text.toString()
                registerAbhaGenerateMobileOtp.txnId = txnId
                abhaViewModel.abhaRequestMobileOtp(
                    registerAbhaGenerateMobileOtp,
                    "Bearer $abhaToken"
                )
            } else {
                Toast.makeText(applicationContext, "Enter Valid Mobile Number", Toast.LENGTH_SHORT)

                    .show()


            }


        })

        binding.btnVerifyOtp.setOnClickListener(View.OnClickListener {

            if (binding.etOtp.otp.toString().length == 6) {

                val verifyRegisterAAadharOtpRequest = RegisterabhaVerifyMobileOtpRequest()
                verifyRegisterAAadharOtpRequest.txnId = txnId
                verifyRegisterAAadharOtpRequest.otp = binding.etOtp.otp
                abhaViewModel.abharegisterverifyMobileOtp(
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
        abhaViewModel.registerabhaGenerateMobileOtpResponse.observe(this, Observer {
            txnId = it.txnId.toString()
            binding.progressBar.visibility = View.GONE
            binding.llMobileNo.visibility = View.GONE
            binding.btnSendOtp.visibility = View.GONE
            binding.llOtp.visibility = View.VISIBLE
            binding.etOtp.requestFocusOTP()
            Toast.makeText(
                applicationContext,
                "Verification code has been sent",
                Toast.LENGTH_SHORT
            ).show()
        })
        abhaViewModel.registerAdharVerifyMobileOtpResponse.observe(this, Observer {
            lifecycleScope.launch {
                dataStoreManager.setTxnId(txnId)


            }
            Toast.makeText(
                applicationContext,
                "Verification Successful",
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(this, CreateAbhaActivity::class.java)
            startActivity(intent)
            finish()
        })

    }


}