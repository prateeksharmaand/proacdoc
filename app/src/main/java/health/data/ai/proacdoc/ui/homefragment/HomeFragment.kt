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

package health.data.ai.proacdoc.ui.homefragment


import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import app.futured.donut.DonutSection
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataPoint
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.DataType.*
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.tasks.Tasks
import health.care.ai.proacdoc.R
import health.care.ai.proacdoc.databinding.FragmentHomeBinding
import health.data.ai.proacdoc.api.models.labtest.login.ProfileTest.Data
import health.data.ai.proacdoc.room.entity.MedicineEntity
import health.data.ai.proacdoc.ui.adapters.HomeProfileTestsAdapter
import health.data.ai.proacdoc.ui.adapters.MedicinesListAdapter
import health.data.ai.proacdoc.ui.addcustomvitalrecord.AddCustomVitalRecordActivity
import health.data.ai.proacdoc.ui.alllabtest.AllLabTestsActivity
import health.data.ai.proacdoc.ui.beni.ProfilesListActivity
import health.data.ai.proacdoc.ui.healthbot.HealthbotwebActivity
import health.data.ai.proacdoc.ui.heartratemonitor.HeartRateMonitorActivity
import health.data.ai.proacdoc.ui.login.LoginViewModel
import health.data.ai.proacdoc.ui.medication.MedicationActivity
import health.data.ai.proacdoc.ui.medication.MedicineViewModel
import health.data.ai.proacdoc.ui.profilelabtestdetails.LabTestProfileDetailsActivity
import health.data.ai.proacdoc.ui.referandearn.ReferandEarnActivity
import health.data.ai.proacdoc.utils.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates


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
    private lateinit var fitnessOptions: FitnessOptions
    private var heartBeat by Delegates.notNull<Float>()
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
        lifecycleScope.launch {
            dataStoreManager.getisGoogleFitLinked.collect { counter ->

                if (counter) {
              //      loadFitnessData()
                    val dateFormat: DateFormat =
                        SimpleDateFormat("dd/MM/yy, hh:mm a", Locale.ENGLISH)
                    val date = Date()
                    binding.txtSyncedGoogleFit.setText("Last synced: " + dateFormat.format(date))
                    binding.cardSyncGoogleFit.visibility = View.GONE

                    binding.txtSyncGoogleFit.visibility = View.GONE
                    binding.txtSyncedGoogleFit.visibility = View.VISIBLE

                    binding.cardSyncGoogleFitDetails.visibility = View.VISIBLE


                } else {
                    binding.cardSyncGoogleFit.visibility = View.VISIBLE
                    binding.txtSyncGoogleFit.visibility = View.VISIBLE
                    binding.txtSyncedGoogleFit.visibility = View.GONE
                    binding.cardSyncGoogleFitDetails.visibility = View.GONE
                }

            }
        }






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

        binding.cardSyncGoogleFit.setOnClickListener(View.OnClickListener {

            loadFitnessData()

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
                try {
                    dataStoreManager.setMiddlevareToken(it.results.data)

                    /* val profileTestRequest = ProfileTestRequest()
                     profileTestRequest.apiKey = it.results.data
                     profileTestRequest.providerId = 1
                     profileTestRequest.type = "Profile"
                     labTestViewModel.getProfileTests(profileTestRequest)*/
                } catch (e: Exception) {
                }


            }

        })
        labTestViewModel.getMiddlewareToken(1)
        labTestViewModel.loading.observe(viewLifecycleOwner, Observer {
            try {
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            } catch (e: Exception) {
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
                // binding.crdAbha.visibility = View.VISIBLE
                //    binding.txtAbha.visibility = View.VISIBLE
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

    fun loadFitnessData() {
        fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_READ)

            .addDataType(DataType.TYPE_CALORIES_EXPENDED, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .build()
        val account = GoogleSignIn.getAccountForExtension(
            requireActivity().applicationContext,
            fitnessOptions
        )
        if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                this, // your activity
                1001, // e.g. 1
                account,
                fitnessOptions
            )
        } else {
            accessGoogleFitCalories()

            lifecycleScope.launch {
                dataStoreManager.setisGoogleFitLinked(true)


            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> when (requestCode) {
                1001 -> {
                    accessGoogleFitCalories()

                    lifecycleScope.launch {
                        dataStoreManager.setisGoogleFitLinked(true)


                    }

                }
                else -> {
                    // Result wasn't from Google Fit
                }
            }
            else -> {
                // Permission not granted
                Toast.makeText(requireActivity(), "Please Grant Permissions", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun accessGoogleFitCalories(){
        val googleSignInAccount =
            GoogleSignIn.getAccountForExtension(requireContext(), fitnessOptions)
        val response = Fitness.getHistoryClient(requireActivity(), googleSignInAccount)
            .readDailyTotal(AGGREGATE_CALORIES_EXPENDED)
        CoroutineScope(Dispatchers.IO).launch {
            val totalSet = Tasks.await(response, 30, TimeUnit.SECONDS)
            dumpDataSet(totalSet)
            accessGoogleFitSteps()

        }


    }
    private fun dumpDataSet(dataSet: DataSet) {
        val format = SimpleDateFormat("MM/dd/yyyy")
        for (dp: DataPoint in dataSet.dataPoints) {
            for (field in dp.dataType.fields) {
                val fieldValue =
                    "Field name: " + field.name.toString() + ", value: " + dp.getValue(field)
                        .asFloat() + "  Start: " + format.format(dp.getStartTime(TimeUnit.MILLISECONDS)) + "  End: " + format.format(
                        dp.getEndTime(TimeUnit.MILLISECONDS)
                    );




                requireActivity().runOnUiThread(Runnable {
                    binding.txtCaloriesCount.text =
                        dp.getValue(field).toString().split(".")[0] + "\nCalories Burned"


                    requireActivity().runOnUiThread(Runnable {
                        binding.txtCalories.text =
                            dp.getValue(field).toString().split(".")[0] .toString()

                    })

                })
            }
        }

    }

    private fun accessGoogleFitSteps() {
        val googleSignInAccount =
            GoogleSignIn.getAccountForExtension(requireContext(), fitnessOptions)
        val response = Fitness.getHistoryClient(requireActivity(), googleSignInAccount)
            .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
        CoroutineScope(Dispatchers.IO).launch {
            val totalSet = Tasks.await(response, 30, TimeUnit.SECONDS)
            dumpDataSetSteps(totalSet)
            accessGoogleFitHeartRate()
        }


    }



    private fun dumpDataSetSteps(dataSet: DataSet) {
        val format = SimpleDateFormat("MM/dd/yyyy")
        for (dp: DataPoint in dataSet.dataPoints) {
            for (field in dp.dataType.fields) {
                val fieldValue =
                    "Field name: " + field.name.toString() + ", value: " + dp.getValue(Field.FIELD_STEPS)
                        .asInt() + "  Start: " + format.format(dp.getStartTime(TimeUnit.MILLISECONDS)) + "  End: " + format.format(
                        dp.getEndTime(TimeUnit.MILLISECONDS)
                    );
                Log.e("steps data", fieldValue)

                requireActivity().runOnUiThread(Runnable {
                    binding.txtSteps.text =
                        dp.getValue(Field.FIELD_STEPS)
                            .asInt().toString()

                })
            }
        }

    }


    private fun accessGoogleFitHeartRate() {
        val googleSignInAccount =
            GoogleSignIn.getAccountForExtension(requireContext(), fitnessOptions)
        val response = Fitness.getHistoryClient(requireActivity(), googleSignInAccount)
            .readDailyTotal(TYPE_HEART_RATE_BPM)
        CoroutineScope(Dispatchers.IO).launch {
            val totalSet = Tasks.await(response, 30, TimeUnit.SECONDS)
            dumpDataSetHeartRate(totalSet)
        }


    }

    private fun dumpDataSetHeartRate(dataSet: DataSet) {
        val format = SimpleDateFormat("MM/dd/yyyy")
        for (dp: DataPoint in dataSet.dataPoints) {
            for (field in dp.dataType.fields) {
                val fieldValue =
                    "Field name: " + field.name.toString() + ", value: " + dp.getValue(Field.FIELD_AVERAGE)
                        .asFloat() + "  Start: " + format.format(dp.getStartTime(TimeUnit.MILLISECONDS)) + "  End: " + format.format(
                        dp.getEndTime(TimeUnit.MILLISECONDS)
                    );
                if (field.name.toString() == "average") {
                    Log.e("steps data", fieldValue)
                    heartBeat=dp.getValue(Field.FIELD_AVERAGE).asFloat()
                    requireActivity().runOnUiThread(Runnable {
                        binding.txtHeartBeats.text =
                            dp.getValue(Field.FIELD_AVERAGE).toString()

                    })


                }
            }
        }


        requireActivity().runOnUiThread(Runnable {
            val section1 = DonutSection(
                name = "Steps",
                color = Color.parseColor("#9D1CB1"),
                amount = 300f
            )

            val section2 = DonutSection(
                name = "Calories",
                color = Color.parseColor("#FAA800"),
                amount = 1479.0966f
            )
            val section3 = DonutSection(
                name = "Heare Rate",
                color = Color.parseColor("#FF3A00"),
                amount = heartBeat
            )

            binding.donutView.cap = 5f
            binding.donutView.submitData(listOf(section1, section2, section3))
           binding. llCounters.visibility=View.VISIBLE

        })

    }

}

