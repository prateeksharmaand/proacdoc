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

package health.data.ai.proacdoc.ui.abhaprofile

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import health.care.ai.proacdoc.BuildConfig
import health.care.ai.proacdoc.R
import health.care.ai.proacdoc.databinding.ActivityAbhaProfileBinding
import health.data.ai.proacdoc.api.models.abhausertoken.AbhaUserTokenRequest
import health.data.ai.proacdoc.application.MainApp
import health.data.ai.proacdoc.ui.abhaconsents.AbhaConsentsActivity
import health.data.ai.proacdoc.ui.createabhaaadharactivity.AbhaViewModel
import health.data.ai.proacdoc.ui.createabhaaadharactivity.CreateAbhaAdharActivity
import health.data.ai.proacdoc.ui.login.LoginViewModel
import health.data.ai.proacdoc.ui.viewabhacard.ViewAbhaCardActivity
import health.data.ai.proacdoc.utils.DataStoreManager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ABHProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAbhaProfileBinding
    private val loginViewModel: LoginViewModel by viewModel()
    private val abhaViewModel: AbhaViewModel by viewModel()
    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var abhaToken: String
    private lateinit var txnId: String
    private var beniid: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAbhaProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        dataStoreManager = DataStoreManager(this)

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
        binding.btnViewAbha.setOnClickListener(View.OnClickListener {

            val intent = Intent(this, ViewAbhaCardActivity::class.java)
            startActivity(intent)

        })
        binding.cardShare.setOnClickListener(View.OnClickListener {

            val appPackageName = BuildConfig.APPLICATION_ID

            val shareBodyText =
                "Create ABHA ID  and get Govt Benefits at   https://play.google.com/store/apps/details?id=$appPackageName"

            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TITLE, "Share With Friends")
                putExtra(Intent.EXTRA_TEXT, shareBodyText)
            }
            startActivity(Intent.createChooser(sendIntent, null))
        })

        binding.btnViewConsents.setOnClickListener(View.OnClickListener {

            val intent = Intent(this, AbhaConsentsActivity::class.java)
            startActivity(intent)
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
                abhaToken = it.results.data.accessToken
                dataStoreManager.setAbhaToken(it.results.data.accessToken)

                lifecycleScope.launch {
                    dataStoreManager.getbeniId.collect { counter ->
                        beniid = counter
                        loginViewModel.getBeniDetails(counter)


                    }
                }

            }
        })
        loginViewModel.benificiaryListResponse.observe(this, Observer {
            try {
                binding.llLoading.visibility = View.GONE
                binding.cardShare.visibility = View.VISIBLE


                Glide.with(MainApp.instance)

                    .load(MainApp.instance.resources.getDrawable(R.drawable.ic_user))
                    .circleCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.imgUser)
            } catch (e: Exception) {
            }


            binding.txtUsername.text =
                it.results.data[0].beniname + " " + it.results.data[0].lastname
            binding.txtOther.text =
                it.results.data[0].gender + " " + it.results.data[0].age + " Years"

            binding.txtAbhaNumber.text =
                it.results.data[0].abhanumber
            var abhaUserTokenRequest = AbhaUserTokenRequest()
            abhaUserTokenRequest.refreshToken = it.results.data[0].refreshToken
            abhaViewModel.getAbhaUserToken(abhaUserTokenRequest, "Bearer $abhaToken")


        })
        abhaViewModel.getabhaUserTokenresponse.observe(this, Observer {

            abhaViewModel.getAbhaUserQr("Bearer $abhaToken", "Bearer " + it.accessToken)
        })
        abhaViewModel.QrResponse.observe(this, Observer {

            val bytes: ByteArray = it.bytes()
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

            Glide.with(MainApp.instance)

                .load(bitmap)

                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imgQr)
        })

    }


}