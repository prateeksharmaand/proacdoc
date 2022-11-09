/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 05/07/22, 1:23 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 05/07/22, 1:23 PM
 ************************************************/

package health.care.ai.ui.homefragment


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import health.care.ai.R
import health.care.ai.api.models.labtest.login.ProfileTest.Data
import health.care.ai.api.models.labtest.login.ProfileTest.ProfileTestRequest

import health.care.ai.databinding.FragmentHomeBinding
import health.care.ai.room.entity.MedicineEntity
import health.care.ai.ui.abha.ABHAwebActivity
import health.care.ai.ui.abhawithapi.AbhaHomeActivity
import health.care.ai.ui.medication.MedicationActivity
import health.care.ai.ui.adapters.HomeProfileTestsAdapter
import health.care.ai.ui.adapters.MedicinesListAdapter
import health.care.ai.ui.addcustomvitalrecord.AddCustomVitalRecordActivity
import health.care.ai.ui.alllabtest.AllLabTestsActivity
import health.care.ai.ui.beni.ProfilesListActivity
import health.care.ai.ui.healthbot.HealthbotwebActivity
import health.care.ai.ui.heartratemonitor.HeartRateMonitorActivity
import health.care.ai.ui.login.LoginViewModel
import health.care.ai.ui.medication.MedicineViewModel
import health.care.ai.ui.profilelabtestdetails.LabTestProfileDetailsActivity
import health.care.ai.ui.referandearn.ReferandEarnActivity
import health.care.ai.utils.DataStoreManager
import health.care.ai.utils.MedicineService
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding


    private val labTestViewModel: LabTestViewModel by viewModel()
    private lateinit var dataStoreManager: DataStoreManager

    private lateinit var linearLayoutManager: LinearLayoutManager
    private val postList = ArrayList<Data>()
    private var postListFull = ArrayList<Data>()
    private lateinit var postAdapter: HomeProfileTestsAdapter
    private val loginViewModel: LoginViewModel by viewModel()
    private val medicineViewModel: MedicineViewModel by viewModel()
    private lateinit var medicineLayoutManager: LinearLayoutManager
    private lateinit var medicineAdapter: MedicinesListAdapter
    private val medicineModel = ArrayList<MedicineEntity>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        dataStoreManager = DataStoreManager(requireContext())
        initUI()
        initObserver()
        loginViewModel.checkLoggedInUserFlow();
        medicineViewModel.getAllFavouriteMedicines()
        return binding.root

    }

    private fun initUI() {
        linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        postAdapter =
            HomeProfileTestsAdapter(HomeProfileTestsAdapter.OnItemClickListener { record ->

                val intent = Intent(activity, LabTestProfileDetailsActivity::class.java)
                intent.putExtra("record", record)
                startActivity(intent)


            }, postList, requireActivity())
        binding.recyclerView.adapter = postAdapter
        binding.recyclerView.itemAnimator = null;

        binding.btnStartheartMonitor.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, HeartRateMonitorActivity::class.java)
            startActivity(intent)
        })



        binding.cardHeartrate.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, HeartRateMonitorActivity::class.java)
            startActivity(intent)
        })
        binding.cardHrtRates.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, HeartRateMonitorActivity::class.java)
            startActivity(intent)
        })

        binding.crdSmartHealth.setOnClickListener(View.OnClickListener {

            Navigation.findNavController(binding.crdSmartHealth).navigate(R.id.smarthealth);
        })

        binding.crdmedicalWallet.setOnClickListener(View.OnClickListener {

            Navigation.findNavController(binding.crdmedicalWallet).navigate(R.id.medicaldocuments);
        })
        binding.cardbViewOrders.setOnClickListener(View.OnClickListener {

            Navigation.findNavController(binding.cardbViewOrders).navigate(R.id.viewOrders);
        })


        binding.crdAbha.setOnClickListener(View.OnClickListener {
             val intent = Intent(activity, ProfilesListActivity::class.java)
             startActivity(intent)




        })


        binding.crdManageDiabetes.setOnClickListener(View.OnClickListener {

            val intent = Intent(activity, AddCustomVitalRecordActivity::class.java)
            intent.putExtra("majorvitalId", 988741)
            startActivity(intent)
        })
        binding.txtviewAll.setOnClickListener(View.OnClickListener {


            val intent = Intent(activity, AllLabTestsActivity::class.java)
            intent.putExtra("record", postListFull)
            startActivity(intent)
        })

        binding.crdRefer.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, ReferandEarnActivity::class.java)
            startActivity(intent)
        })
        binding.crdHealthBot.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, HealthbotwebActivity::class.java)
            startActivity(intent)


        })

        binding.crdTrackMedicine.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, MedicationActivity::class.java)
            startActivity(intent)


        })
        medicineLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewMedicines.layoutManager = medicineLayoutManager
        medicineAdapter =
            MedicinesListAdapter(MedicinesListAdapter.OnItemClickListener { record ->
                val intent = Intent(activity, MedicationActivity::class.java)
                startActivity(intent)

            }, medicineModel, requireActivity())
        binding.recyclerViewMedicines.adapter = medicineAdapter
        binding.recyclerViewMedicines.itemAnimator = null;

    }


    fun initObserver() {


        labTestViewModel.middlevareToken.observe(viewLifecycleOwner, Observer {

            lifecycleScope.launch {
                dataStoreManager.setMiddlevareToken(it.results.data)
/*
                val profileTestRequest = ProfileTestRequest()
                profileTestRequest.apiKey = it.results.data
                profileTestRequest.providerId = 1
                profileTestRequest.type = "Profile"
                labTestViewModel.getProfileTests(profileTestRequest)*/


            }

        })
        labTestViewModel.getMiddlewareToken(1)
        labTestViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
        labTestViewModel.profileTestResponse.observe(viewLifecycleOwner, Observer {
            postListFull = it.results.data as ArrayList<Data>
            binding.lvTest.visibility = View.VISIBLE
            postAdapter.updateEmployeeListItems(it.results.data.take(10).toMutableList())
        })

        loginViewModel.userDetails.observe(viewLifecycleOwner, Observer {
            if (it.EmailAddress == "FKDML7W3YCUYETMU67HV33J4X4-00@cloudtestlabaccounts.com" || it.EmailAddress == "FKDML7W3YCUYETMU67HV33J4X4-00@cloudtestlabaccounts.com" || it.EmailAddress == "M4F3AUSOAHVHFGRTT3E4RAXAYE-00@cloudtestlabaccounts.com" || it.EmailAddress == "s1074pratignya4727@elearnkvsroblr.in" || it.EmailAddress == "s1074pratignya4727@elearnkvsroblr.in") {
                binding.crdAbha.visibility = View.GONE
                binding.txtAbha.visibility = View.GONE
            } else {
                binding.crdAbha.visibility = View.VISIBLE
                binding.txtAbha.visibility = View.VISIBLE
            }
        })
        medicineViewModel.medicineList.observe(viewLifecycleOwner, Observer {

            if (!it.isEmpty()) {
                binding.txtTitleFavourite.visibility = View.VISIBLE
            } else {
                binding.txtTitleFavourite.visibility = View.GONE
            }
            medicineAdapter.updateEmployeeListItems(it.take(5).toMutableList())
        })

    }


}

