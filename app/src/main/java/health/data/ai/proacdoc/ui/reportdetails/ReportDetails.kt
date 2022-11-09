/*************************************************
 * Created by Efendi Hariyadi on 03/08/22, 2:03 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 03/08/22, 2:03 PM
 ************************************************/

package health.data.ai.proacdoc.ui.reportdetails

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import health.care.ai.proacdoc.R
import health.care.ai.proacdoc.databinding.ActivityReportDetailsBinding

import health.data.ai.proacdoc.api.models.User.UserModel
import health.data.ai.proacdoc.application.MainApp

import health.data.ai.proacdoc.ui.adapters.MedicalRecordsDetailsAdapter
import health.data.ai.proacdoc.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ReportDetails : AppCompatActivity() {

    private lateinit var binding: ActivityReportDetailsBinding
    private lateinit var user: UserModel
    private val loginViewModel: LoginViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        loginViewModel.userDetails.observe(this, Observer {
            user = it
        })
        loginViewModel.checkLoggedInUserFlow()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId== android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    fun initUI() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = MedicalRecordsDetailsAdapter(supportFragmentManager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_share -> {

                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "Lab Test Report of "+user.Name+"  "+MainApp.pdfUrl)
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)


                    true
                }

                else -> false
            }
        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

}