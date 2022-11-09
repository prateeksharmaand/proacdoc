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

package health.care.ai.ui.abhawithapi

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.*
import android.provider.Settings
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import health.care.ai.R
import health.care.ai.api.models.medicinetime.MedicineTimings
import health.care.ai.databinding.ActivityAbhaHomeBinding
import health.care.ai.databinding.ActivityAddmedicationBinding
import health.care.ai.room.entity.MedicineEntity
import health.care.ai.room.entity.MedicineTimingsEntity
import health.care.ai.ui.adapters.TimeListAdapter
import health.care.ai.ui.createabhaaadharactivity.CreateAbhaAdharActivity
import health.care.ai.ui.medication.MedicineViewModel
import health.care.ai.ui.profilelabtestdetails.LabTestProfileDetailsActivity
import health.care.ai.utils.MedicineService
import health.care.ai.utils.Utils
import kotlinx.android.synthetic.main.activity_addmedication.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.teamgravity.imageradiobutton.GravityImageRadioButton
import java.text.SimpleDateFormat
import java.util.*


class AbhaHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAbhaHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAbhaHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()

    }

    fun initUI() {


        setSupportActionBar(binding.toolbar)


        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener(View.OnClickListener {

            onBackPressed()

        })

        binding.btnCreateAbha.setOnClickListener(View.OnClickListener {

            val intent = Intent(this, CreateAbhaAdharActivity::class.java)
            startActivity(intent)
        })


    }


}