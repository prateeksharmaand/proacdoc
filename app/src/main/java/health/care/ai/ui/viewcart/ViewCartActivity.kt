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

package health.care.ai.ui.viewcart

import android.content.Context
import android.content.ContextWrapper
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
import com.google.android.play.core.review.ReviewManagerFactory
import health.care.ai.R
import health.care.ai.api.models.User.UserModel
import health.care.ai.api.models.heartrate.AddHeartRateRequest
import health.care.ai.api.models.smarthealthresponse.Data
import health.care.ai.api.models.smarthealthresponse.Vitaldetails
import health.care.ai.application.MainApp

import health.care.ai.databinding.ActivityHeartRateDetailsBinding
import health.care.ai.databinding.ActivitySmarthealthdetailsBinding
import health.care.ai.databinding.ActivityViewcartBinding
import health.care.ai.room.entity.Cart
import health.care.ai.ui.adapters.CartItemsAdapter
import health.care.ai.ui.adapters.SmartHealthAdapter
import health.care.ai.ui.adapters.SmartHealthVitalDetailsAdapter
import health.care.ai.ui.heartratemonitor.HeartRateMonitorActivity
import health.care.ai.ui.homefragment.LabTestViewModel
import health.care.ai.ui.labtestfinaldetails.LabTestFinalDetailsActivity
import health.care.ai.ui.labvitalstrend.LabVitalsViewModel
import health.care.ai.ui.login.LoginViewModel
import health.care.ai.utils.Utils.CompanionClass.Companion.setAppLocale
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ViewCartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewcartBinding
    private val labTestViewModel: LabTestViewModel by viewModel()


    private lateinit var postAdapter: CartItemsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewcartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initObserver()
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

        binding.btnCheckout.setOnClickListener(View.OnClickListener {

            val intent = Intent(this, LabTestFinalDetailsActivity::class.java)
            startActivity(intent)
            finish()
        })
    }

    fun initObserver() {


        labTestViewModel.cartItems.observe(this, Observer {
            it?.let { list ->

                linearLayoutManager =
                    LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                binding.recyclerView.layoutManager = linearLayoutManager
                postAdapter =
                    CartItemsAdapter(CartItemsAdapter.OnItemClickListener { record ->

                        labTestViewModel.removeFromCart(record)
                        Toast.makeText(
                            applicationContext,
                            "Test Removed From Card Successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                    }, list.toMutableList(), this)
                binding.recyclerView.adapter = postAdapter
                binding.recyclerView.itemAnimator = null;
                val costs = list.sumOf { it.Rate!!.toInt() }
                if (costs == 0) {
                    binding.rlCheckOut.visibility = View.GONE
                    binding.llempty.visibility = View.VISIBLE
                    binding.cardHomeCollection.visibility = View.GONE

                    binding.cardInfo.visibility = View.GONE

                } else {
                    binding.cardHomeCollection.visibility = View.VISIBLE
                    binding.rlCheckOut.visibility = View.VISIBLE
                    binding.llempty.visibility = View.GONE
                    binding.cardInfo.visibility = View.VISIBLE
                }
                binding.txtTotal.text =
                    MainApp.instance.resources.getString(R.string.rs, costs.toString())


            }
        })
        labTestViewModel.getCartItems()
    }

}