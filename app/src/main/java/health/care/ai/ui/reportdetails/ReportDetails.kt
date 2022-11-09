/*************************************************
 * Created by Efendi Hariyadi on 03/08/22, 2:03 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 03/08/22, 2:03 PM
 ************************************************/

package health.care.ai.ui.reportdetails

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import health.care.ai.R
import health.care.ai.api.models.User.UserModel
import health.care.ai.application.MainApp
import health.care.ai.databinding.ActivityReportDetailsBinding
import health.care.ai.ui.adapters.MedicalRecordsDetailsAdapter
import health.care.ai.ui.login.LoginViewModel
import health.care.ai.utils.Utils.CompanionClass.Companion.setAppLocale
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