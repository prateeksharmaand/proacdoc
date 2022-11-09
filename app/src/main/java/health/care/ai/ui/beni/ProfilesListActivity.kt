package health.care.ai.ui.beni


import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.vvalidator.field.FieldError
import com.afollestad.vvalidator.form
import com.afollestad.vvalidator.form.FormResult
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.Gson
import health.care.ai.R
import health.care.ai.api.models.Order.PostOrder.OrderRequest
import health.care.ai.api.models.User.UserModel
import health.care.ai.api.models.appointmentslots.AppointmentSlotsRequest
import health.care.ai.api.models.benificiary.add.AddBeniRequest
import health.care.ai.api.models.pincodeavaiblity.PinCodeAvaiblityRequest
import health.care.ai.api.models.smarthealthresponse.Data
import health.care.ai.application.MainApp
import health.care.ai.databinding.ActivityLabTestFinalDetailsBinding
import health.care.ai.databinding.ActivityProfilesListBinding
import health.care.ai.room.entity.Cart
import health.care.ai.ui.abhawithapi.AbhaHomeActivity
import health.care.ai.ui.adapters.AppointmentsSlotSpinnerAdapter
import health.care.ai.ui.adapters.BenificiariesListAdapter
import health.care.ai.ui.adapters.CartItemsAdapter
import health.care.ai.ui.adapters.ProfilesListAdapter
import health.care.ai.ui.createabhaaadharactivity.CreateAbhaAdharActivity
import health.care.ai.ui.homefragment.LabTestViewModel
import health.care.ai.ui.login.LoginViewModel
import health.care.ai.ui.registerabhaactivity.CreateAbhaActivity
import health.care.ai.utils.DataStoreManager
import health.care.ai.utils.Utils
import health.care.ai.utils.Utils.CompanionClass.Companion.setAppLocale
import kotlinx.android.synthetic.main.activity_lab_test_final_details.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.redundent.kotlin.xml.xml
import java.io.File.separator
import java.util.*


class ProfilesListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfilesListBinding

    private val loginViewModel: LoginViewModel by viewModel()


    private lateinit var user: UserModel

    private lateinit var dataStoreManager: DataStoreManager




    private lateinit var postAdapter: ProfilesListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilesListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        dataStoreManager = DataStoreManager(this)
        initUI()
        initObserver()
        loginViewModel.checkLoggedInUserFlow();


    }


    fun initUI() {

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener(View.OnClickListener { onBackPressed() })




        binding.btnBeni.setOnClickListener(View.OnClickListener {

            openeAddBottomSheet()
        })


    }


    fun initObserver() {


        loginViewModel.userDetails.observe(this, Observer {
            user = it
            loginViewModel.getBeni(user.UserId!!)


        })




        loginViewModel.benificiaryListResponse.observe(this) {
            it?.let { list ->
                binding.progressBar.visibility = View.GONE
                if (list.results.data.size > 0) {

                    linearLayoutManager =
                        LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                    binding.recyclerViewBenificiaries.layoutManager = linearLayoutManager
                    postAdapter =
                        ProfilesListAdapter(ProfilesListAdapter.OnItemClickListener { record ->

                            lifecycleScope.launch {
                                dataStoreManager.setbeniId(record.baniid)
                            }
                            if(record.abhanumber.isNullOrEmpty())
                            {
                                val intent = Intent(this, AbhaHomeActivity::class.java)
                                startActivity(intent)
                            }
                            else
                            {


                            }



                        }, list.results.data.toMutableList(), this)
                    binding.recyclerViewBenificiaries.adapter = postAdapter
                    binding.recyclerViewBenificiaries.itemAnimator = null;


                } else {


                }

            }
        }


        loginViewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })


        loginViewModel.addBeniResponse.observe(this, Observer {

            loginViewModel.getBeni(user.UserId!!.toInt())

        })




    }



    fun openeAddBottomSheet() {
        val list_of_items = arrayOf("Male", "Female")

        val bottomSheetDialog = BottomSheetDialog(this, R.style.TransparentDialog)
        bottomSheetDialog.setContentView(R.layout.add_beni_bottomsheet)

        val btnupdate = bottomSheetDialog.findViewById<MaterialButton>(R.id.btnupdate)
        val etName = bottomSheetDialog.findViewById<EditText>(R.id.etFirstName)
        val etLastName = bottomSheetDialog.findViewById<EditText>(R.id.etLastName)

        val etDOB = bottomSheetDialog.findViewById<EditText>(R.id.etDOB)
        etDOB!!.setOnClickListener(View.OnClickListener {
            val builderRange = MaterialDatePicker.Builder.datePicker()


            val pickerRange = builderRange.build()
            pickerRange.show(supportFragmentManager, pickerRange.toString())
            pickerRange.addOnPositiveButtonClickListener {
                Utils.CompanionClass.hideKeyboard(etDOB, applicationContext)
                val strCal: Calendar = GregorianCalendar.getInstance()
                strCal.time= Date(it)


                val calendarEnd: Calendar = GregorianCalendar.getInstance()


                val year: Int = calendarEnd.get(Calendar.YEAR)
                val dobyear: Int = strCal.get(Calendar.YEAR)
                val age: Int = year - dobyear
                etDOB.setText(age.toString())

            }
        })

        val spinnerGender = bottomSheetDialog.findViewById<Spinner>(R.id.spinnerGender)

        btnupdate?.setOnClickListener(View.OnClickListener {

            if (etName!!.text.isNullOrBlank()) {
                etName!!.error = "Enter First Name"
                return@OnClickListener
            } else  if (etLastName!!.text.isNullOrBlank()) {
                etLastName!!.error = "Enter Last Name"
                return@OnClickListener
            } else {

                if (etDOB!!.text.isNullOrBlank()) {
                    etDOB!!.error = "Enter Bate of Birth"
                    return@OnClickListener
                } else {
                    try {
                        etName.error = null
                        etDOB.error = null
                        val addBeniRequest = AddBeniRequest()
                        addBeniRequest.Age = etDOB.text.toString()
                        addBeniRequest.lastname = etLastName.text.toString()
                        addBeniRequest.BeniUserId = user.UserId
                        addBeniRequest.Beniname = etName.text.toString()
                        val gender = spinnerGender!!.selectedItem.toString()
                        addBeniRequest.Gender = gender.toString()
                        loginViewModel.Addbeni(addBeniRequest)
                        bottomSheetDialog.dismiss()
                    } catch (e: Exception) {
                    }
                }
            }


        })

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_items)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerGender!!.setAdapter(adapter)


        bottomSheetDialog.show()

    }

}