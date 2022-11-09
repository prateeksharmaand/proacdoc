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

package health.care.ai.ui.profilelabtestdetails

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.play.core.review.ReviewManagerFactory
import health.care.ai.R
import health.care.ai.api.models.User.UserModel
import health.care.ai.api.models.heartrate.AddHeartRateRequest
import health.care.ai.api.models.labtest.login.ProfileTest.Data

import health.care.ai.api.models.smarthealthresponse.Vitaldetails
import health.care.ai.application.MainApp

import health.care.ai.databinding.ActivityHeartRateDetailsBinding
import health.care.ai.databinding.ActivityProfileTestDetailsBinding
import health.care.ai.databinding.ActivitySmarthealthdetailsBinding
import health.care.ai.room.entity.Cart
import health.care.ai.ui.adapters.LabProfileTestChildsAdapter
import health.care.ai.ui.adapters.SmartHealthAdapter
import health.care.ai.ui.adapters.SmartHealthVitalDetailsAdapter
import health.care.ai.ui.addcustomvitalrecord.AddCustomVitalRecordActivity
import health.care.ai.ui.heartratemonitor.HeartRateMonitorActivity
import health.care.ai.ui.homefragment.LabTestViewModel
import health.care.ai.ui.labvitalstrend.LabVitalsViewModel
import health.care.ai.ui.login.LoginViewModel
import health.care.ai.ui.viewcart.ViewCartActivity
import health.care.ai.utils.Utils.CompanionClass.Companion.setAppLocale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


class LabTestProfileDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileTestDetailsBinding
    private val vitalsViewModel: LabVitalsViewModel by viewModel()
    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var user: UserModel
    private lateinit var data: Data

    private lateinit var postAdapter: LabProfileTestChildsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val labTestViewModel: LabTestViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileTestDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        data = intent.extras!!.get("record") as Data
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener(View.OnClickListener { onBackPressed() })
        binding.testCount.text = data.testCount + " Tests"
        binding.txtName.text = data.name
        if (data.fasting.equals("CF")) {
            binding.cardFasting.visibility = View.VISIBLE
        }
        linearLayoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        postAdapter =
            LabProfileTestChildsAdapter(LabProfileTestChildsAdapter.OnItemClickListener { record ->
            }, data.testlist.toMutableList(), this)
        binding.recyclerView.adapter = postAdapter
        binding.recyclerView.itemAnimator = null;


        binding.testDiscuntedrate.text =
            MainApp.instance.resources.getString(R.string.rs, data.rate)
    //    binding.testRate.text = MainApp.instance.resources.getString(R.string.rs, data.rate)
     //   binding.testRate.setPaintFlags(binding.testRate.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)


        var percentage = (data.discount.toDouble() / data.rate.toDouble()) * 100

        binding.testDiscount.text = percentage.roundToInt().toString() + "% discount"

        binding.btnAddToCart.setOnClickListener(View.OnClickListener {

            if (binding.btnAddToCart.text == resources.getString(R.string.add_to_cart)) {

                try {
                    val cart = Cart()
                    cart.Name = data.name
                    cart.Code=data.code
                    cart.Rate = data.rate
                    cart.TotalTests = data.testCount
                    cart.TestList = data.testlist.joinToString { it.name }
                        .split(",")
                        .toString().drop(1)
                        .dropLast(1)
                    labTestViewModel.AddToCart(cart)
                    Toast.makeText(applicationContext, "Test Added To Cart", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ViewCartActivity::class.java)
                    startActivity(intent)
                    finish()
                } catch (e: Exception) {
                }
            } else {
                val intent = Intent(this, ViewCartActivity::class.java)
                startActivity(intent)
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
        labTestViewModel.cartItemExists.observe(this, Observer {
            if (it) {
                binding.btnAddToCart.text = resources.getString(R.string.view_cart)
            } else {



                binding.btnAddToCart.text = resources.getString(R.string.add_to_cart)
            }

        })
        labTestViewModel.checkIfExistsInCart(data.name)

    }

}