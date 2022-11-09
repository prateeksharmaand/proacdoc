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

package health.care.ai.ui.labvitalstrend

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.ScatterChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.ScatterData
import com.github.mikephil.charting.data.ScatterDataSet
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import health.care.ai.R
import health.care.ai.api.models.User.UserModel
import health.care.ai.api.models.medicalreport.AddnewLabVital.AddLabVitalRequest
import health.care.ai.api.models.medicalreport.smartreportresponse.Data
import health.care.ai.databinding.ActivityViewLabVitalsTrendBinding
import health.care.ai.ui.login.LoginViewModel
import health.care.ai.utils.Utils.CompanionClass.Companion.toEditable
import org.koin.androidx.viewmodel.ext.android.viewModel


class ViewLabVitalsTrendActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewLabVitalsTrendBinding
    private val vitalsViewModel: LabVitalsViewModel by viewModel()
    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var user: UserModel
    val record = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewLabVitalsTrendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initObserver()
        loginViewModel.checkLoggedInUserFlow();
    }

    fun initUI() {

        val record = intent.extras!!.get("record") as Data
        binding.toolbar.title =
            record.testname + " - " + record.vitaldetails.get(0).normalizedText
        setSupportActionBar(binding.toolbar)
        binding.btnEdit.setOnClickListener(View.OnClickListener {

            openeditBottomSheet()
        })
        binding.btnAdd.setOnClickListener(View.OnClickListener {

            openeAddBottomSheet()
        })
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener(View.OnClickListener { onBackPressed() })
        try {
            if (record.vitaldetails.isNotEmpty()) {


                if (record.vitaldetails.get(0).normalvalues != "Undetermined") {


                    binding.txtVitalDescTitle.text =
                        "Why " + binding.toolbar.title + " is important to me?"


                    if (!record.vitaldetails[0].description.isNullOrBlank()) {
                        binding.txtVitalDesc.text = record.vitaldetails[0].description
                        binding.cardVitalDescription.visibility = View.VISIBLE
                    }


                    val array = record.vitaldetails.get(0).normalvalues.split("-")

                    if (array.size == 2) {
                        val min = array[0].replace(",", "")
                        val max = array[1].replace(",", "")
                        binding.chart1.description.text = "Normal Ranges:" + min + " - " + max
                        binding.chart1.description.textSize = 14f
                        val newvalue = record.testvalue.replace(",", "").replace("^", "")
                        if (newvalue.toDouble() < min.toDouble()) {
                            binding.txtStatus.text = "Your " + binding.toolbar.title + " is Low"
                            binding.cardStatus.visibility = View.VISIBLE


                        } else
                            if (newvalue.toDouble() > max.toDouble()) {
                                binding.txtStatus.text =
                                    "Your " + binding.toolbar.title + " is High"
                                binding.cardStatus.visibility = View.VISIBLE

                            } else {
                                if (newvalue.toDouble() > min.toDouble() && newvalue.toDouble() < max.toDouble()) {
                                    binding.txtStatus.text =
                                        "Your " + binding.toolbar.title + " is Normal"
                                    binding.cardStatus.visibility = View.VISIBLE

                                } else {
                                    binding.cardStatus.visibility = View.GONE
                                }
                            }


                    } else {
                        binding.cardStatus.visibility = View.GONE
                    }


                }
            } else {
                binding.cardVitalDescription.visibility = View.GONE
                binding.cardStatus.visibility = View.GONE
            }
        } catch (e: Exception) {
        }

    }

    fun initObserver() {
        loginViewModel.userDetails.observe(this, Observer {
            user = it
            val record = intent.extras!!.get("record") as Data
            vitalsViewModel.getvitalCharts(user.UserId, record.vitalId)

        })

        vitalsViewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
        vitalsViewModel.updateVitalValueResponse.observe(this, Observer {
            it?.let { list ->
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                finish()
            }
        })
        vitalsViewModel.addLabVitalResponse.observe(this, Observer {
            it?.let { list ->
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                finish()
            }
        })


        vitalsViewModel.vitalChartsResponse.observe(this, Observer {
            val dataSets: ArrayList<IScatterDataSet> = ArrayList()


            for (vitalChartData in it.results.data) {
                val ourPieEntry = ArrayList<Entry>()
                ourPieEntry.add(
                    Entry(
                        vitalChartData.testvalue.replace(",", "").replace("^", "").toFloat(),
                        vitalChartData.testvalue.replace(",", "").replace("^", "").toFloat()
                    )
                )
                val set1 = ScatterDataSet(ourPieEntry, vitalChartData.dated)
                set1.setScatterShape(ScatterChart.ScatterShape.CIRCLE)
                set1.color = ColorTemplate.JOYFUL_COLORS[0]
                set1.scatterShapeSize = 8f
                set1.valueTextSize = 10f;
                dataSets.add(set1)
            }


            val data = ScatterData(dataSets)
            binding.chart1.axisRight.setDrawLabels(false);
            binding.chart1.xAxis.setPosition(XAxis.XAxisPosition.TOP);
            binding.chart1.xAxis.gridColor = Color.GRAY
            binding.chart1.animateY(2000);
            binding.chart1.getAxisLeft().setDrawGridLines(false);
            binding.chart1.getXAxis().setDrawGridLines(false);
            binding.chart1.data = data
            binding.chart1.invalidate()

        })


    }

    fun openeditBottomSheet() {
        val record = intent.extras!!.get("record") as Data
        val bottomSheetDialog = BottomSheetDialog(this, R.style.TransparentDialog)
        bottomSheetDialog.setContentView(R.layout.update_vitals_lab_bottomsheet)

        val btnupdate = bottomSheetDialog.findViewById<MaterialButton>(R.id.btnupdate)
        val ettestname = bottomSheetDialog.findViewById<EditText>(R.id.ettestname)

        ettestname!!.text = record.testname.toEditable()
        val etTestValue = bottomSheetDialog.findViewById<EditText>(R.id.etTestValue)
        etTestValue!!.text = record.testvalue.toEditable()


        btnupdate?.setOnClickListener(View.OnClickListener {

            if (ettestname.text.isNullOrBlank()) {
                ettestname.error = "Enter Test Name"
                return@OnClickListener
            } else {
                if (etTestValue.text.isNullOrBlank()) {
                    etTestValue.error = "Enter Test Value"
                    return@OnClickListener
                } else {
                    try {
                        ettestname.error = null
                        etTestValue.error = null
                        vitalsViewModel.UpdateVitalValue(
                            record.mraiId,
                            etTestValue.text.toString(),
                            ettestname.text.toString()
                        )
                        bottomSheetDialog.dismiss()
                    } catch (e: Exception) {
                    }
                }
            }


        })


        bottomSheetDialog.show()

    }

    fun openeAddBottomSheet() {
        var list_of_items = arrayOf("g/dl", "g/L", "10*6/mm3", "10*6/ul", "10*12/L")
        val record = intent.extras!!.get("record") as Data
        val bottomSheetDialog = BottomSheetDialog(this, R.style.TransparentDialog)
        bottomSheetDialog.setContentView(R.layout.add_vitals_lab_bottomsheet)

        val btnupdate = bottomSheetDialog.findViewById<MaterialButton>(R.id.btnupdate)
        val ettestname = bottomSheetDialog.findViewById<EditText>(R.id.ettestname)


        val etTestValue = bottomSheetDialog.findViewById<EditText>(R.id.etTestValue)

        val labunitSpinner = bottomSheetDialog.findViewById<Spinner>(R.id.labunitSpinner)

        btnupdate?.setOnClickListener(View.OnClickListener {

            if (ettestname!!.text.isNullOrBlank()) {
                ettestname!!.error = "Enter Test Name"
                return@OnClickListener
            } else {
                if (etTestValue!!.text.isNullOrBlank()) {
                    etTestValue!!.error = "Enter Test Value"
                    return@OnClickListener
                } else {
                    try {
                        ettestname.error = null
                        etTestValue.error = null
                        val addLabVitalRequest = AddLabVitalRequest()
                        addLabVitalRequest.UserId = user.UserId
                        addLabVitalRequest.recordId=record.recordId
                        addLabVitalRequest.Dated = record.dated
                        addLabVitalRequest.data =
                            ettestname.text.toString() + " " + etTestValue.text.toString() + " " + labunitSpinner!!.selectedItem.toString()
                        vitalsViewModel.addNewLabVital(
                            addLabVitalRequest
                        )
                        bottomSheetDialog.dismiss()
                    } catch (e: Exception) {
                    }
                }
            }


        })

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_items)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        labunitSpinner!!.setAdapter(adapter)


        bottomSheetDialog.show()

    }
}