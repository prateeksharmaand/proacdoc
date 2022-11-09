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

package health.care.ai.ui.addmedication

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
import health.care.ai.databinding.ActivityAddmedicationBinding
import health.care.ai.room.entity.MedicineEntity
import health.care.ai.room.entity.MedicineTimingsEntity
import health.care.ai.ui.adapters.TimeListAdapter
import health.care.ai.ui.medication.MedicineViewModel
import health.care.ai.utils.MedicineService
import health.care.ai.utils.Utils
import kotlinx.android.synthetic.main.activity_addmedication.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.teamgravity.imageradiobutton.GravityImageRadioButton
import java.text.SimpleDateFormat
import java.util.*


class AddMedicationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddmedicationBinding
    private var position = 0
    private lateinit var timelistAdapter: TimeListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val medicineViewModel: MedicineViewModel by viewModel()

    val medicineTimingsList: MutableList<MedicineTimings> = mutableListOf()
    private var shape = 0
    private var medicineColor = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddmedicationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        ignoreBatteryOptimization()
    }

    fun initUI() {


        setSupportActionBar(binding.toolbar)


        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener(View.OnClickListener {

            onBackPressed()

        })
        binding.gravityGroup.setOnCheckedChangeListener { _, radioButton, _, _ ->
            shape = (radioButton as GravityImageRadioButton).tag.toString().toInt()
        }
        binding.stepView.done(false)
        binding.button.setOnClickListener {
            when (position) {
                0 -> {

                    if (binding.etMedicineName.text.isBlank()) {
                        binding.etMedicineName.error = "Enter Medicine name"
                        binding.etMedicineName.requestFocus()
                    } else {
                        binding.txtMedName.text = binding.etMedicineName.text
                        binding.etMedicineName.error = ""
                        binding.etMedicineName.clearFocus()

                        binding.one.visibility = View.GONE
                        binding.two.visibility = View.VISIBLE
                        position = 1
                        binding.stepView.done(false)
                        binding.stepView.go(position, true)
                        binding.button.text = "Next"
                        Utils.CompanionClass.hideKeyboard(it, applicationContext)
                    }


                }

                1 -> {
                    binding.two.visibility = View.GONE
                    binding.three.visibility = View.VISIBLE
                    position = 2
                    binding.stepView.done(false)
                    binding.stepView.go(position, true)
                    binding.button.text = "Next"
                    Utils.CompanionClass.hideKeyboard(it, applicationContext)
                }
                2 -> {

                    if (binding.etStrength.text.isBlank()) {
                        binding.etStrength.error = "Enter Medicine Strength"
                        binding.etStrength.requestFocus()
                    } else {


                        var txtStrDetail = binding.etStrength.text.toString()


                        val strengthType: List<Int> = binding.chipGroupStrengthType.checkedChipIds
                        for (id in strengthType) {
                            val chips: Chip = binding.chipGroupStrengthType.findViewById(id)

                            txtStrDetail += " " + chips.text.toString()
                        }

                        val ids: List<Int> = binding.chipGroupMedicineTpe.checkedChipIds
                        for (id in ids) {
                            val chip: Chip = binding.chipGroupMedicineTpe.findViewById(id)
                            txtStrDetail += " " + chip.text.toString()
                        }

                        binding.txtmedDetail.text = txtStrDetail
                        binding.etStrength.error = ""
                        binding.etStrength.clearFocus()
                        binding.three.visibility = View.GONE
                        binding.four.visibility = View.VISIBLE
                        position = 3
                        binding.stepView.done(false)
                        binding.stepView.go(position, true)
                        binding.button.text = "Next"
                        Utils.CompanionClass.hideKeyboard(it, applicationContext)
                    }

                }
                3 -> {

                    if (binding.etStartDate.text.isBlank()) {
                        binding.etStartDate.error = "Enter Medicine Start Date"
                        binding.etStartDate.requestFocus()
                    } else if (medicineTimingsList.size == 0) {
                        binding.etStartDate.error = ""
                        binding.etStartDate.clearFocus()
                        Toast.makeText(
                            applicationContext,
                            "Add Medicine Timings",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        val timings = medicineTimingsList.joinToString() { it.timings }
                        binding.txtmedDateTime.text =
                            "From " + binding.etStartDate.text + " Timings " + timings
                        binding.four.visibility = View.GONE
                        binding.five.visibility = View.VISIBLE
                        position = 4
                        binding.stepView.done(false)
                        binding.stepView.go(position, true)
                        binding.button.text = "Next"
                        Utils.CompanionClass.hideKeyboard(it, applicationContext)
                        binding.crdMedicinecolor.performClick()


                    }
                }
                4 -> {

                    if (shape == 0) {
                        Toast.makeText(
                            applicationContext,
                            "Select Medicine Shape",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else if (medicineColor == "") {
                        Toast.makeText(
                            applicationContext,
                            "Please Medicine Color",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        when (shape) {
                            1 -> {
                                binding.imgShape.setImageDrawable(resources.getDrawable(R.drawable.stwo))
                            }
                            2 -> {
                                binding.imgShape.setImageDrawable(resources.getDrawable(R.drawable.sthree))
                            }
                            3 -> {
                                binding.imgShape.setImageDrawable(resources.getDrawable(R.drawable.sfour))
                            }
                            4 -> {
                                binding.imgShape.setImageDrawable(resources.getDrawable(R.drawable.sfive))
                            }
                            5 -> {
                                binding.imgShape.setImageDrawable(resources.getDrawable(R.drawable.ssix))
                            }
                        }

                        binding.faColorSelected.setTextColor(binding.faColor.textColors)
                        binding.five.visibility = View.GONE
                        binding.six.visibility = View.VISIBLE
                        position = 5
                        binding.stepView.done(false)
                        binding.stepView.go(position, true)
                        binding.button.text = "Submit"
                        Utils.CompanionClass.hideKeyboard(it, applicationContext)
                    }


                }
                else -> {
                    val medicineEntity = MedicineEntity()
                    val mid = Random().nextInt(10000)
                    medicineEntity.medicineName = binding.etMedicineName.text.toString()
                    val ids: List<Int> = binding.chipGroupMedicineTpe.checkedChipIds
                    for (id in ids) {
                        val chip: Chip = binding.chipGroupMedicineTpe.findViewById(id)
                        medicineEntity.MedicineType = chip.text.toString()
                    }
                    medicineEntity.Strength = binding.etStrength.text.toString().toInt()


                    val strengthType: List<Int> = binding.chipGroupStrengthType.checkedChipIds
                    for (id in strengthType) {
                        val chip: Chip = binding.chipGroupStrengthType.findViewById(id)
                        medicineEntity.StrengthUnit = chip.text.toString()
                    }
                    when (binding.radioFrequency.checkedRadioButtonId) {
                        binding.radioRegular.id -> {
                            medicineEntity.Frequency = 1
                        }
                        binding.radioSpecificDays.id -> {
                            medicineEntity.Frequency = 2
                        }
                        binding.radioAsNeeded.id -> {
                            medicineEntity.Frequency = 3
                        }
                    }
                    medicineEntity.FrequencyDuration = binding.txtFrequency.text.toString()
                    medicineEntity.StartDate = binding.etStartDate.text.toString()
                    val timings = medicineTimingsList.joinToString() { it.timings }
                        .filter { !it.isWhitespace() }
                    medicineEntity.Timings = timings

                    medicineEntity.Id = mid
                    medicineEntity.Shape = shape
                    medicineEntity.Color = medicineColor
                    medicineEntity.Isfavourite = true
                    medicineViewModel.saveMedicine(medicineEntity)




                    when (medicineEntity.Frequency) {
                        1 -> {
                            try {
                                val times = medicineEntity.Timings!!.split(",").toTypedArray()

                                for (timed in times) {
                                    val requestcode = Random().nextInt(2000)
                                    val alarmMgr0 =
                                        getSystemService(Context.ALARM_SERVICE) as AlarmManager
                                    val intent0 = Intent(this, MedicineService::class.java)
                                    var title =
                                        etStrength.text.toString() + " " + etMedicineName.text.toString() + " " + medicineEntity.MedicineType
                                    intent0.putExtra("message", title)
                                    intent0.putExtra("id", medicineEntity.Id)
                                    val pendingIntent0 =
                                        PendingIntent.getService(
                                            applicationContext,
                                            requestcode,
                                            intent0,
                                            0
                                        )

                                    val medicineTimings=MedicineTimingsEntity()
                                    medicineTimings.Id=requestcode
                                    medicineTimings.medicineId=medicineEntity.Id
                                    medicineViewModel.AddMedicineTiming(medicineTimings)
                                    when (medicineEntity.FrequencyDuration) {
                                        "Every Day" -> {
                                            val timeOff9 = Calendar.getInstance()
                                            val time = timed!!.split(":").toTypedArray()

                                            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                                            timeOff9.time = medicineEntity.StartDate?.let { it1 ->
                                                dateFormat.parse(
                                                    it1
                                                )
                                            }!!;

                                            timeOff9[Calendar.HOUR_OF_DAY] = time[0].toInt()
                                            timeOff9[Calendar.MINUTE] = time[1].toInt()
                                            timeOff9[Calendar.SECOND] = 0
                                            alarmMgr0.setInexactRepeating(
                                                AlarmManager.RTC_WAKEUP,
                                                timeOff9.timeInMillis,
                                                1 * 24 * 60 * 60 * 1000,
                                                pendingIntent0
                                            )

                                        }
                                        "Every Other Day" -> {
                                            val timeOff9 = Calendar.getInstance()
                                            val time = timed!!.split(":").toTypedArray()

                                            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                                            timeOff9.time = medicineEntity.StartDate?.let { it1 ->
                                                dateFormat.parse(
                                                    it1
                                                )
                                            }!!;

                                            timeOff9[Calendar.HOUR_OF_DAY] = time[0].toInt()
                                            timeOff9[Calendar.MINUTE] = time[1].toInt()
                                            timeOff9[Calendar.SECOND] = 0
                                            alarmMgr0.setInexactRepeating(
                                                AlarmManager.RTC_WAKEUP,
                                                timeOff9.timeInMillis,
                                                2 * 24 * 60 * 60 * 1000,
                                                pendingIntent0
                                            )

                                        }
                                        "Every Three Days" -> {
                                            val timeOff9 = Calendar.getInstance()
                                            val time = timed!!.split(":").toTypedArray()

                                            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                                            timeOff9.time = medicineEntity.StartDate?.let { it1 ->
                                                dateFormat.parse(
                                                    it1
                                                )
                                            }!!;

                                            timeOff9[Calendar.HOUR_OF_DAY] = time[0].toInt()
                                            timeOff9[Calendar.MINUTE] = time[1].toInt()
                                            timeOff9[Calendar.SECOND] = 0
                                            alarmMgr0.setInexactRepeating(
                                                AlarmManager.RTC_WAKEUP,
                                                timeOff9.timeInMillis,
                                                3 * 24 * 60 * 60 * 1000,
                                                pendingIntent0
                                            )

                                        }
                                        "Every Four Days" -> {
                                            val timeOff9 = Calendar.getInstance()
                                            val time = timed!!.split(":").toTypedArray()

                                            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                                            timeOff9.time = medicineEntity.StartDate?.let { it1 ->
                                                dateFormat.parse(
                                                    it1
                                                )
                                            }!!;

                                            timeOff9[Calendar.HOUR_OF_DAY] = time[0].toInt()
                                            timeOff9[Calendar.MINUTE] = time[1].toInt()
                                            timeOff9[Calendar.SECOND] = 0
                                            alarmMgr0.setInexactRepeating(
                                                AlarmManager.RTC_WAKEUP,
                                                timeOff9.timeInMillis,
                                                4 * 24 * 60 * 60 * 1000,
                                                pendingIntent0
                                            )

                                        }
                                        "Every Five Days" -> {
                                            val timeOff9 = Calendar.getInstance()
                                            val time = timed!!.split(":").toTypedArray()

                                            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                                            timeOff9.time = medicineEntity.StartDate?.let { it1 ->
                                                dateFormat.parse(
                                                    it1
                                                )
                                            }!!;

                                            timeOff9[Calendar.HOUR_OF_DAY] = time[0].toInt()
                                            timeOff9[Calendar.MINUTE] = time[1].toInt()
                                            timeOff9[Calendar.SECOND] = 0
                                            alarmMgr0.setInexactRepeating(
                                                AlarmManager.RTC_WAKEUP,
                                                timeOff9.timeInMillis,
                                                5 * 24 * 60 * 60 * 1000,
                                                pendingIntent0
                                            )

                                        }
                                        "Every Six Days" -> {
                                            val timeOff9 = Calendar.getInstance()
                                            val time = timed!!.split(":").toTypedArray()

                                            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                                            timeOff9.time = medicineEntity.StartDate?.let { it1 ->
                                                dateFormat.parse(
                                                    it1
                                                )
                                            }!!;

                                            timeOff9[Calendar.HOUR_OF_DAY] = time[0].toInt()
                                            timeOff9[Calendar.MINUTE] = time[1].toInt()
                                            timeOff9[Calendar.SECOND] = 0
                                            alarmMgr0.setInexactRepeating(
                                                AlarmManager.RTC_WAKEUP,
                                                timeOff9.timeInMillis,
                                                6 * 24 * 60 * 60 * 1000,
                                                pendingIntent0
                                            )

                                        }
                                    }
                                }
                                Toast.makeText(
                                    applicationContext,
                                    "Reminder for " + etMedicineName.text + " is scheduled",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            } catch (e: Exception) {
                                Log.e("Exception", e.localizedMessage)
                            }

                        }
                        2 -> {


                            try {
                                val times = medicineEntity.Timings!!.split(",").toTypedArray()

                                for (timed in times) {
                                    val requestcode = Random().nextInt(2000)
                                    val alarmMgr0 =
                                        getSystemService(Context.ALARM_SERVICE) as AlarmManager
                                    val intent0 = Intent(this, MedicineService::class.java)
                                    var title =
                                        etStrength.text.toString() + " " + etMedicineName.text.toString() + " " + medicineEntity.MedicineType
                                    intent0.putExtra("message", title)
                                    intent0.putExtra("id", medicineEntity.Id)
                                    val pendingIntent0 =
                                        PendingIntent.getService(
                                            applicationContext,
                                            requestcode,
                                            intent0,
                                            0
                                        )
                                    val medicineTimings=MedicineTimingsEntity()
                                    medicineTimings.Id=requestcode
                                    medicineTimings.medicineId=medicineEntity.Id
                                    medicineViewModel.AddMedicineTiming(medicineTimings)
                                    when (medicineEntity.FrequencyDuration) {
                                        "Every Sunday" -> {
                                            val timeOff9 = Calendar.getInstance()
                                            val time = timed!!.split(":").toTypedArray()

                                            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                                            timeOff9.time = medicineEntity.StartDate?.let { it1 ->
                                                dateFormat.parse(
                                                    it1
                                                )
                                            }!!;
                                            timeOff9.set(Calendar.DAY_OF_WEEK, 1);
                                            timeOff9[Calendar.HOUR_OF_DAY] = time[0].toInt()
                                            timeOff9[Calendar.MINUTE] = time[1].toInt()
                                            timeOff9[Calendar.SECOND] = 0
                                            alarmMgr0.setInexactRepeating(
                                                AlarmManager.RTC_WAKEUP,
                                                timeOff9.timeInMillis,
                                                7 * 24 * 60 * 60 * 1000,
                                                pendingIntent0
                                            )

                                        }
                                        "Every Monday" -> {
                                            val timeOff9 = Calendar.getInstance()
                                            val time = timed!!.split(":").toTypedArray()

                                            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                                            timeOff9.time = medicineEntity.StartDate?.let { it1 ->
                                                dateFormat.parse(
                                                    it1
                                                )
                                            }!!;
                                            timeOff9.set(Calendar.DAY_OF_WEEK, 2);
                                            timeOff9[Calendar.HOUR_OF_DAY] = time[0].toInt()
                                            timeOff9[Calendar.MINUTE] = time[1].toInt()
                                            timeOff9[Calendar.SECOND] = 0
                                            alarmMgr0.setInexactRepeating(
                                                AlarmManager.RTC_WAKEUP,
                                                timeOff9.timeInMillis,
                                                7 * 24 * 60 * 60 * 1000,
                                                pendingIntent0
                                            )

                                        }
                                        "Every Tuesday" -> {
                                            val timeOff9 = Calendar.getInstance()
                                            val time = timed!!.split(":").toTypedArray()

                                            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                                            timeOff9.time = medicineEntity.StartDate?.let { it1 ->
                                                dateFormat.parse(
                                                    it1
                                                )
                                            }!!;
                                            timeOff9.set(Calendar.DAY_OF_WEEK, 3);
                                            timeOff9[Calendar.HOUR_OF_DAY] = time[0].toInt()
                                            timeOff9[Calendar.MINUTE] = time[1].toInt()
                                            timeOff9[Calendar.SECOND] = 0
                                            alarmMgr0.setInexactRepeating(
                                                AlarmManager.RTC_WAKEUP,
                                                timeOff9.timeInMillis,
                                                7 * 24 * 60 * 60 * 1000,
                                                pendingIntent0
                                            )

                                        }
                                        "Every Wednesday" -> {
                                            val timeOff9 = Calendar.getInstance()
                                            val time = timed!!.split(":").toTypedArray()

                                            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                                            timeOff9.time = medicineEntity.StartDate?.let { it1 ->
                                                dateFormat.parse(
                                                    it1
                                                )
                                            }!!;
                                            timeOff9.set(Calendar.DAY_OF_WEEK, 4);
                                            timeOff9[Calendar.HOUR_OF_DAY] = time[0].toInt()
                                            timeOff9[Calendar.MINUTE] = time[1].toInt()
                                            timeOff9[Calendar.SECOND] = 0
                                            alarmMgr0.setInexactRepeating(
                                                AlarmManager.RTC_WAKEUP,
                                                timeOff9.timeInMillis,
                                                7 * 24 * 60 * 60 * 1000,
                                                pendingIntent0
                                            )

                                        }
                                        "Every Thursday" -> {
                                            val timeOff9 = Calendar.getInstance()
                                            val time = timed!!.split(":").toTypedArray()

                                            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                                            timeOff9.time = medicineEntity.StartDate?.let { it1 ->
                                                dateFormat.parse(
                                                    it1
                                                )
                                            }!!;
                                            timeOff9.set(Calendar.DAY_OF_WEEK, 5);
                                            timeOff9[Calendar.HOUR_OF_DAY] = time[0].toInt()
                                            timeOff9[Calendar.MINUTE] = time[1].toInt()
                                            timeOff9[Calendar.SECOND] = 0
                                            alarmMgr0.setInexactRepeating(
                                                AlarmManager.RTC_WAKEUP,
                                                timeOff9.timeInMillis,
                                                7 * 24 * 60 * 60 * 1000,
                                                pendingIntent0
                                            )

                                        }
                                        "Every Friday" -> {
                                            val timeOff9 = Calendar.getInstance()
                                            val time = timed!!.split(":").toTypedArray()

                                            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                                            timeOff9.time = medicineEntity.StartDate?.let { it1 ->
                                                dateFormat.parse(
                                                    it1
                                                )
                                            }!!;
                                            timeOff9.set(Calendar.DAY_OF_WEEK, 6);
                                            timeOff9[Calendar.HOUR_OF_DAY] = time[0].toInt()
                                            timeOff9[Calendar.MINUTE] = time[1].toInt()
                                            timeOff9[Calendar.SECOND] = 0
                                            alarmMgr0.setInexactRepeating(
                                                AlarmManager.RTC_WAKEUP,
                                                timeOff9.timeInMillis,
                                                7 * 24 * 60 * 60 * 1000,
                                                pendingIntent0
                                            )

                                        }
                                        "Every Saturday" -> {
                                            val timeOff9 = Calendar.getInstance()
                                            val time = timed!!.split(":").toTypedArray()

                                            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                                            timeOff9.time = medicineEntity.StartDate?.let { it1 ->
                                                dateFormat.parse(
                                                    it1
                                                )
                                            }!!;
                                            timeOff9.set(Calendar.DAY_OF_WEEK, 7);
                                            timeOff9[Calendar.HOUR_OF_DAY] = time[0].toInt()
                                            timeOff9[Calendar.MINUTE] = time[1].toInt()
                                            timeOff9[Calendar.SECOND] = 0
                                            alarmMgr0.setInexactRepeating(
                                                AlarmManager.RTC_WAKEUP,
                                                timeOff9.timeInMillis,
                                                7 * 24 * 60 * 60 * 1000,
                                                pendingIntent0
                                            )

                                        }
                                    }
                                }
                                Toast.makeText(
                                    applicationContext,
                                    "Reminder for " + etMedicineName.text + " is scheduled",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            } catch (e: Exception) {
                                Log.e("Exception", e.localizedMessage)
                            }


                        }
                        3 -> {


                            try {
                                val times = medicineEntity.Timings!!.split(",").toTypedArray()

                                for (timed in times) {
                                    val requestcode = Random().nextInt(2000)
                                    val alarmMgr0 =
                                        getSystemService(Context.ALARM_SERVICE) as AlarmManager
                                    val intent0 = Intent(this, MedicineService::class.java)
                                    var title =
                                        etStrength.text.toString() + " " + etMedicineName.text.toString() + " " + medicineEntity.MedicineType
                                    intent0.putExtra("message", title)
                                    intent0.putExtra("id", medicineEntity.Id)
                                    val pendingIntent0 =
                                        PendingIntent.getService(
                                            applicationContext,
                                            requestcode,
                                            intent0,
                                            0
                                        )
                                    val timeOff9 = Calendar.getInstance()
                                    val time = timed!!.split(":").toTypedArray()

                                    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                                    timeOff9.time = medicineEntity.StartDate?.let { it1 ->
                                        dateFormat.parse(
                                            it1
                                        )
                                    }!!;

                                    timeOff9[Calendar.HOUR_OF_DAY] = time[0].toInt()
                                    timeOff9[Calendar.MINUTE] = time[1].toInt()
                                    timeOff9[Calendar.SECOND] = 0
                                    alarmMgr0.setWindow(
                                        AlarmManager.RTC_WAKEUP,
                                        timeOff9.timeInMillis,
                                        1 * 24 * 60 * 60 * 1000,
                                        pendingIntent0
                                    )
                                    val medicineTimings=MedicineTimingsEntity()
                                    medicineTimings.Id=requestcode
                                    medicineTimings.medicineId=medicineEntity.Id
                                    medicineViewModel.AddMedicineTiming(medicineTimings)
                                }
                                Toast.makeText(
                                    applicationContext,
                                    "Reminder for " + etMedicineName.text + " is scheduled",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            } catch (e: Exception) {
                                Log.e("Exception", e.localizedMessage)
                            }


                        }
                    }


                    // Go to another Activity or Fragment
                }
            }


        }
        binding.crdMedicinecolor.setOnClickListener(View.OnClickListener {
            MaterialColorPickerDialog
                .Builder(this)
                .setTitle("Pick Medicine Color")
                .setColorShape(ColorShape.CIRCLE)
                .setColorSwatch(ColorSwatch._300)

                .setColorListener { color, colorHex ->
                    medicineColor = colorHex
                    binding.faColor.setTextColor(color)
                }
                .show()

        })

        binding.crdFreQuency.setOnClickListener(View.OnClickListener {

            binding.radioFrequency.visibility = View.VISIBLE


            binding.radioIntervelRegular.visibility = View.VISIBLE
            binding.txtChooseInterval.visibility = View.VISIBLE
            binding.radioIntervelSpecificDays.visibility = View.GONE
            binding.radioRegular.isChecked = true

        })
        binding.radioFrequency.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->

            when (checkedId) {
                binding.radioRegular.id -> {


                    binding.radioIntervelRegular.visibility = View.VISIBLE
                    binding.radioIntervelSpecificDays.visibility = View.GONE
                    binding.txtFrequency.text = "Every Day"

                }
                binding.radioSpecificDays.id -> {

                    binding.radioIntervelSpecificDays.visibility = View.VISIBLE

                    binding.radioIntervelRegular.visibility = View.GONE
                    binding.txtFrequency.text = "Every Sunday"

                }
                binding.radioAsNeeded.id -> {

                    binding.txtChooseInterval.visibility = View.GONE
                    binding.radioIntervelRegular.visibility = View.GONE
                    binding.radioIntervelSpecificDays.visibility = View.GONE
                    binding.txtFrequency.text = "As Needed"

                }
            }
        })


        binding.etStartDate.setOnClickListener(View.OnClickListener {


            val builderRange = MaterialDatePicker.Builder.datePicker()

            builderRange.setCalendarConstraints(limitRange().build())
            val pickerRange = builderRange.build()
            pickerRange.show(supportFragmentManager, pickerRange.toString())
            pickerRange.addOnPositiveButtonClickListener {
                binding.etStartDate.setError(null)
                binding.etStartDate.clearFocus();
                Utils.CompanionClass.hideKeyboard(binding.etStartDate, applicationContext)
                val dateStringnew: String = DateFormat.format("dd/MM/yyyy", Date(it)).toString()
                binding.etStartDate.setText(dateStringnew)
            }
        })


        binding.radioIntervelRegular.setOnCheckedChangeListener { chipGroup, id ->
            val chip: Chip = chipGroup.findViewById(id)
            binding.txtFrequency.text = chip.text
            binding.txtChooseInterval.visibility = View.GONE
            binding.radioIntervelRegular.visibility = View.GONE

            binding.radioFrequency.visibility = View.GONE


        }
        binding.radioIntervelSpecificDays.setOnCheckedChangeListener { chipGroup, id ->
            val chip: Chip = chipGroup.findViewById(id)
            binding.txtFrequency.text = chip.text
            binding.txtChooseInterval.visibility = View.GONE
            binding.radioIntervelSpecificDays.visibility = View.GONE
            binding.radioFrequency.visibility = View.GONE


        }
        linearLayoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewTime.layoutManager = linearLayoutManager
        timelistAdapter =
            TimeListAdapter(TimeListAdapter.OnItemClickListener { record ->
                medicineTimingsList.remove(record)
                timelistAdapter.updateData(medicineTimingsList)


            }, medicineTimingsList.toMutableList(), this)
        binding.recyclerViewTime.adapter = timelistAdapter
        binding.recyclerViewTime.visibility = View.VISIBLE
        binding.recyclerViewTime.itemAnimator = null;
        binding.llAddMedicineTime.setOnClickListener(View.OnClickListener {

            val mTimePicker: TimePickerDialog
            val mcurrentTime = Calendar.getInstance()
            val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
            val minute = mcurrentTime.get(Calendar.MINUTE)

            mTimePicker = TimePickerDialog(
                this,
                { view, hourOfDay, minute ->


                    val medicineTimings = MedicineTimings(String.format("%d:%d", hourOfDay, minute))
                    medicineTimingsList.add(medicineTimings)
                    timelistAdapter.updateData(medicineTimingsList)


                }, hour, minute, false
            )
            mTimePicker.show()
        })

    }

    /*   override fun onBackPressed() {
           when (position) {


               0 -> {
                   super.onBackPressed()
               }
               1 -> {
                   binding.one.visibility = View.GONE
                   binding.two.visibility = View.VISIBLE
                   position = 0
                   binding.stepView.done(false)
                   binding.stepView.go(position, true)
                   binding.button.text = "Next"
               }

               2 -> {
                   binding.two.visibility = View.GONE
                   binding.three.visibility = View.VISIBLE
                   position = 1
                   binding.stepView.done(false)
                   binding.stepView.go(position, true)
                   binding.button.text = "Next"
               }
               3 -> {
                   binding.three.visibility = View.GONE
                   binding.four.visibility = View.VISIBLE
                   position = 2
                   binding.stepView.done(false)
                   binding.stepView.go(position, true)
                   binding.button.text = "Next"
               }
               4 -> {
                   binding.four.visibility = View.GONE
                   binding.five.visibility = View.VISIBLE
                   position = 3
                   binding.stepView.done(false)
                   binding.stepView.go(position, true)
                   binding.button.text = "Next"
               }
               5 -> {
                   binding.five.visibility = View.GONE
                   binding.six.visibility = View.VISIBLE
                   position = 4
                   binding.stepView.done(false)
                   binding.stepView.go(position, true)
                   binding.button.text = "Submit"
               }
               else -> {

                   binding.stepView.done(true)


                   // Go to another Activity or Fragment
               }
           }
       }*/

    private fun limitRange(): CalendarConstraints.Builder {

        val constraintsBuilderRange = CalendarConstraints.Builder()

        val calendarStart: Calendar = GregorianCalendar.getInstance()
        val calendarEnd: Calendar = GregorianCalendar.getInstance()


        val year: Int = calendarStart.get(Calendar.YEAR)
        val month: Int = calendarStart.get(Calendar.MONTH)
        val dayOfMonth: Int = calendarStart.get(Calendar.DAY_OF_MONTH)
        calendarStart.set(year, month, dayOfMonth - 1)


        val dt = Date()

        calendarEnd.time = dt
        calendarEnd.add(Calendar.DATE, 30)


        val minDate = calendarStart.timeInMillis
        val maxDate = calendarEnd.timeInMillis

        constraintsBuilderRange.setStart(minDate)
        constraintsBuilderRange.setEnd(maxDate)

        constraintsBuilderRange.setValidator(RangeValidator(minDate, maxDate))

        return constraintsBuilderRange
    }

    class RangeValidator(private val minDate: Long, private val maxDate: Long) :
        CalendarConstraints.DateValidator {


        constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readLong()
        )

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            TODO("not implemented")
        }

        override fun describeContents(): Int {
            TODO("not implemented")
        }

        override fun isValid(date: Long): Boolean {
            return !(minDate >= date || maxDate < date)

        }

        companion object CREATOR : Parcelable.Creator<RangeValidator> {
            override fun createFromParcel(parcel: Parcel): RangeValidator {
                return RangeValidator(parcel)
            }

            override fun newArray(size: Int): Array<RangeValidator?> {
                return arrayOfNulls(size)
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun ignoreBatteryOptimization() {
        val intent = Intent()
        val packN = packageName
        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        if (!pm.isIgnoringBatteryOptimizations(packN)) {
            intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
            intent.data = Uri.parse("package:$packN")
            startActivity(intent)
        }
    }
}