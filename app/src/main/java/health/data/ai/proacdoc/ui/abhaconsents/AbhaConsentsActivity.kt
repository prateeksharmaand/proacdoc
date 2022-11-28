/*************************************************
 * Created by Efendi Hariyadi on 03/08/22, 2:03 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 03/08/22, 2:03 PM
 ************************************************/

package health.data.ai.proacdoc.ui.abhaconsents

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import health.care.ai.proacdoc.R
import health.care.ai.proacdoc.databinding.ActivityAbhaConsentsBinding
import health.care.ai.proacdoc.databinding.ActivityReportDetailsBinding

import health.data.ai.proacdoc.api.models.User.UserModel
import health.data.ai.proacdoc.application.MainApp
import health.data.ai.proacdoc.ui.adapters.AbhaConsentsAdapter

import health.data.ai.proacdoc.ui.adapters.MedicalRecordsDetailsAdapter
import health.data.ai.proacdoc.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class AbhaConsentsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAbhaConsentsBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAbhaConsentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()

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

        navController = Navigation.findNavController(this, R.id.activity_main_nav_host_fragment)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
        val appBarConfiguration = AppBarConfiguration(setOf()
        )
        binding.toolbar.setupWithNavController(navController,appBarConfiguration)


    }


}