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

package health.care.ai.ui.medication

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import health.care.ai.BuildConfig
import health.care.ai.databinding.ActivityMedicationBinding
import health.care.ai.room.entity.MedicineEntity
import health.care.ai.ui.adapters.MedicinesFullListAdapter
import health.care.ai.ui.addmedication.AddMedicationActivity
import health.care.ai.utils.MedicineService
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MedicationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMedicationBinding
    private val medicineViewModel: MedicineViewModel by viewModel()

    private lateinit var medicineLayoutManager: LinearLayoutManager
    private lateinit var medicineAdapter: MedicinesFullListAdapter
    private val medicineModel = ArrayList<MedicineEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        initObserver()
        if (intent != null && intent.hasExtra("REQUEST_CODE")) {
            val action = intent.extras!!["REQUEST_CODE"] as Int?
            val id = intent.extras!!["id"] as Int?
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            if (action != null) {
                if (action == 151) {

                    medicineViewModel.updationMedicationTakenStatus(true, currentDate, id)
                } else if (action == 152) {
                    medicineViewModel.updationMedicationTakenStatus(false, currentDate, id)
                }
            }
        }
        medicineViewModel.getAllMedicines()

    }

    fun initObserver() {

        medicineViewModel.medicineList.observe(this, Observer {
            if (it.isEmpty()) {


                binding.recyclerViewMedicines.visibility = View.GONE
                binding.crdNoAddedMedicine.visibility = View.VISIBLE
            } else {
                binding.crdNoAddedMedicine.visibility = View.GONE
                binding.recyclerViewMedicines.visibility = View.VISIBLE
            }
            medicineAdapter.updateEmployeeListItems(it.toMutableList())


        })

        medicineViewModel.medicineAlarmTimingsList.observe(this, Observer {

            for (medicineTiming in it) {


                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val myIntent = Intent(applicationContext, MedicineService::class.java)
                val pendingIntent = medicineTiming.Id?.let { it1 ->
                    PendingIntent.getService(
                        applicationContext, it1, myIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                }

                alarmManager.cancel(pendingIntent)




                medicineViewModel.deleteMedicineTiming(medicineTiming.Id)
            }


        })


    }

    fun initUI() {


        setSupportActionBar(binding.toolbar)


        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener(View.OnClickListener {

            onBackPressed()

        })
        binding.btnAddMedication.setOnClickListener(View.OnClickListener {

            val intent = Intent(this, AddMedicationActivity::class.java)
            startActivity(intent)
        })

        binding.floatingActionButton.setOnClickListener(View.OnClickListener {

            val intent = Intent(this, AddMedicationActivity::class.java)
            startActivity(intent)
        })
        binding.crdExportMedicine.setOnClickListener(View.OnClickListener {


        })


        medicineLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewMedicines.layoutManager = medicineLayoutManager
        medicineAdapter =
            MedicinesFullListAdapter(MedicinesFullListAdapter.OnItemClickListener { record, type ->
                when (type) {
                    1 -> {
                        medicineViewModel.updateFavourite(record.Isfavourite, record.Id)
                        Toast.makeText(applicationContext, "Updated", Toast.LENGTH_SHORT).show()
                    }
                    2 -> {

                        record.Id?.let { medicineViewModel.getMedicineTiming(it) }



                        medicineViewModel.delete(record)
                    }
                }

            }, medicineModel, this)
        binding.recyclerViewMedicines.adapter = medicineAdapter
        binding.recyclerViewMedicines.itemAnimator = null;


    }

    override fun onResume() {
        super.onResume()
        NotificationManagerCompat.from(this).cancelAll()
    }



}