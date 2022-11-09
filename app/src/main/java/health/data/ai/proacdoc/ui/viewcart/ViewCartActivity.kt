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

package health.data.ai.proacdoc.ui.viewcart

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.play.core.review.ReviewManagerFactory
import health.care.ai.proacdoc.R
import health.care.ai.proacdoc.databinding.ActivityViewcartBinding

import health.data.ai.proacdoc.application.MainApp


import health.data.ai.proacdoc.ui.adapters.CartItemsAdapter
import health.data.ai.proacdoc.ui.homefragment.LabTestViewModel
import health.data.ai.proacdoc.ui.labtestfinaldetails.LabTestFinalDetailsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


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