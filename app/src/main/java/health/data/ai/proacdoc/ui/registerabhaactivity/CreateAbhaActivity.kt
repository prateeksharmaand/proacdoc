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

package health.data.ai.proacdoc.ui.registerabhaactivity

import android.os.*
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import health.care.ai.proacdoc.databinding.ActivityAbhaCreateBinding
import health.data.ai.proacdoc.api.models.addAbhaToProfile.AddAbhaToProfileRequest
import health.data.ai.proacdoc.api.models.registerabha.RegisterAbhaRequest

import health.data.ai.proacdoc.ui.createabhaaadharactivity.AbhaViewModel
import health.data.ai.proacdoc.ui.login.LoginViewModel
import health.data.ai.proacdoc.utils.DataStoreManager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class CreateAbhaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAbhaCreateBinding
    private val loginViewModel: LoginViewModel by viewModel()
    private val abhaViewModel: AbhaViewModel by viewModel()
    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var abhaToken: String
    private lateinit var txnId: String
    private var beniid: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAbhaCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        dataStoreManager = DataStoreManager(this)
        lifecycleScope.launch {
            dataStoreManager.getAbhaToken.collect { counter ->
                abhaToken = counter


            }
        }
        lifecycleScope.launch {
            dataStoreManager.getTxnId.collect { counter ->
                txnId = counter


            }
        }
        lifecycleScope.launch {
            dataStoreManager.getbeniId.collect { counter ->
                beniid = counter
                loginViewModel.getBeniDetails(counter)


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
        binding.btnCreateAbha.setOnClickListener(View.OnClickListener {

            val registerAbhaRequest = RegisterAbhaRequest()
            registerAbhaRequest.txnId = txnId
            registerAbhaRequest.firstName = binding.etfirst.text.toString()
            registerAbhaRequest.lastName = binding.etlast.text.toString()
            registerAbhaRequest.healthId = binding.etGealthid.text.toString()
            registerAbhaRequest.password = binding.etPassword.text.toString()
            registerAbhaRequest.email = "abc@gmail.com"
            registerAbhaRequest.middleName = ""
            registerAbhaRequest.name = binding.etfirst.text.toString()
            registerAbhaRequest.profilePhoto = ""
            abhaViewModel.createAbhaId(
                registerAbhaRequest,
                "Bearer $abhaToken"
            )


        })


    }

    fun initObserver() {


        abhaViewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
        loginViewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })


        abhaViewModel.registerAbhaResponse.observe(this, Observer {

            Toast.makeText(
                applicationContext,
                "ABHA Card created Successfully ",
                Toast.LENGTH_SHORT
            ).show()
            val registerAbhaRequest = AddAbhaToProfileRequest()
            registerAbhaRequest.abhaid=it.healthIdNumber
            registerAbhaRequest.abhanumber=it.healthId
            registerAbhaRequest.baniid=beniid
            registerAbhaRequest.token=it.token
            registerAbhaRequest.refreshToken=it.refreshToken
            abhaViewModel.addAbhaToBeni(
                registerAbhaRequest
            )


        })


        abhaViewModel.registererrorAbhaResponse.observe(this, Observer {

            Toast.makeText(
                applicationContext,
                it.details[0].message,
                Toast.LENGTH_LONG
            ).show()
            binding.txtError.text = it.details[0].message


        })

        loginViewModel.benificiaryListResponse.observe(this, Observer {

            binding.etfirst.setText(it.results.data[0].beniname)
            binding.etlast.setText(it.results.data[0].lastname)


        })

        abhaViewModel.addAbhaToProfileResponse.observe(this, Observer {

            Toast.makeText(
                applicationContext,
                it.message,
                Toast.LENGTH_LONG
            ).show()


        })


    }


}