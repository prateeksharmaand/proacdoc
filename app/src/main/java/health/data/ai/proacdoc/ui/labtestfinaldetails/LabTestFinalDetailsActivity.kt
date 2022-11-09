package health.data.ai.proacdoc.ui.labtestfinaldetails


import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
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
import health.care.ai.proacdoc.R
import health.care.ai.proacdoc.databinding.ActivityLabTestFinalDetailsBinding

import health.data.ai.proacdoc.api.models.Order.PostOrder.OrderRequest
import health.data.ai.proacdoc.api.models.User.UserModel
import health.data.ai.proacdoc.api.models.appointmentslots.AppointmentSlotsRequest
import health.data.ai.proacdoc.api.models.benificiary.add.AddBeniRequest
import health.data.ai.proacdoc.api.models.pincodeavaiblity.PinCodeAvaiblityRequest
import health.data.ai.proacdoc.application.MainApp

import health.data.ai.proacdoc.room.entity.Cart
import health.data.ai.proacdoc.ui.adapters.AppointmentsSlotSpinnerAdapter
import health.data.ai.proacdoc.ui.adapters.BenificiariesListAdapter
import health.data.ai.proacdoc.ui.homefragment.LabTestViewModel
import health.data.ai.proacdoc.ui.login.LoginViewModel
import health.data.ai.proacdoc.utils.DataStoreManager
import health.data.ai.proacdoc.utils.Utils
import kotlinx.android.synthetic.main.activity_lab_test_final_details.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class LabTestFinalDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLabTestFinalDetailsBinding

    private val loginViewModel: LoginViewModel by viewModel()
    private val labTestViewModel: LabTestViewModel by viewModel()

    private lateinit var user: UserModel

    private lateinit var dataStoreManager: DataStoreManager
    lateinit var appointmentsSlotSpinnerAdapter: AppointmentsSlotSpinnerAdapter
    private lateinit var middleWareToken: String


    var bneiList: List<health.data.ai.proacdoc.api.models.benificiary.get.Data>? = null
    var cartList: List<Cart>? = null

    var pasingDate: String? = null
    var totalRate: Int? = null
    private lateinit var postAdapter: BenificiariesListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLabTestFinalDetailsBinding.inflate(layoutInflater)
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
        binding.etDate.setOnClickListener(View.OnClickListener {


            val builderRange = MaterialDatePicker.Builder.datePicker()

            builderRange.setCalendarConstraints(limitRange().build())
            val pickerRange = builderRange.build()
            pickerRange.show(supportFragmentManager, pickerRange.toString())
            pickerRange.addOnPositiveButtonClickListener {
                binding.etDate.setError(null)
                binding.etDate.clearFocus();
                Utils.CompanionClass.hideKeyboard(binding.etDate, applicationContext)
                val dateString: String = DateFormat.format("MM/dd/yyyy", Date(it)).toString()


                pasingDate = DateFormat.format("yyyy/MM/dd", Date(it)).toString()
                val appointmentSlotsRequest = AppointmentSlotsRequest()
                appointmentSlotsRequest.Pincode = etPinCode.text.toString()
                appointmentSlotsRequest.ApiKey = middleWareToken
                appointmentSlotsRequest.Date = dateString
                appointmentSlotsRequest.ProviderId = 1.toString()
                labTestViewModel.getAppointmentTime(appointmentSlotsRequest)


                val dateStringnew: String = DateFormat.format("dd/MM/yyyy", Date(it)).toString()
                binding.etDate.setText(dateStringnew)
            }
        })


        binding.etPinCode.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length == 6) {
                    Utils.CompanionClass.hideKeyboard(binding.etPinCode, applicationContext)
                    binding.txtpinStatus.visibility = View.GONE
                    binding.txtAppointmentTitle.visibility = View.GONE
                    binding.cardAppointment.visibility = View.GONE
                    binding.etDate.text.clear()
                    val pinCodeAvaiblityRequest = PinCodeAvaiblityRequest()
                    pinCodeAvaiblityRequest.providerId = 1.toString()
                    pinCodeAvaiblityRequest.Pincode = s.toString()
                    pinCodeAvaiblityRequest.apiKey = middleWareToken
                    labTestViewModel.verifyPinCodeAvaiblity(pinCodeAvaiblityRequest)
                } else {
                    binding.txtAppointmentTitle.visibility = View.GONE
                    binding.cardAppointment.visibility = View.GONE
                    binding.etDate.text.clear()
                }
            }
        })
        binding.btnBeni.setOnClickListener(View.OnClickListener {

            openeAddBottomSheet()
        })
        binding.needReport.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                labTestViewModel.setReportRequired(75)
            } else {
                labTestViewModel.setReportRequired(0)
            }
        }
        binding.btnPlaceOrder.setOnClickListener(View.OnClickListener {

            val myForm = form {
                input(binding.etMobileNo) {
                    isNotEmpty()
                    length().exactly(10)
                    onErrors { view, errors ->
                        val firstError: FieldError? = errors.firstOrNull()
                        if (firstError != null) {
                            view.error = firstError.toString()
                            view.requestFocus()
                        }

                    }
                }
                input(binding.etAddress) {
                    isNotEmpty()
                    length().greaterThan(20)
                    onErrors { view, errors ->
                        val firstError: FieldError? = errors.firstOrNull()
                        if (firstError != null) {
                            view.error = firstError.toString()
                            view.requestFocus()
                        }

                    }
                }
                input(binding.etPinCode) {
                    isNotEmpty()
                    length().exactly(6)
                    onErrors { view, errors ->
                        val firstError: FieldError? = errors.firstOrNull()
                        if (firstError != null) {
                            view.error = firstError.toString()
                            view.requestFocus()
                        }

                    }
                }
                input(binding.etDate) {
                    binding.etDate.setError(null)
                    binding.etDate.clearFocus();

                    onErrors { view, errors ->
                        val firstError: FieldError? = errors.firstOrNull()
                        if (firstError != null) {
                            view.error = firstError.toString()
                            view.requestFocus()
                        }

                    }
                }

            }
            val result: FormResult = myForm.validate()
            val isSuccess: Boolean = result.success()
            if (isSuccess) {


                if (bneiList.isNullOrEmpty()) {
                    Toast.makeText(
                        applicationContext,
                        "Please add atleast one benificiary",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    var benilist = ""
                    benilist=  "<NewDataSet>"
                    for (person in bneiList!!) {

                        val ch = person.gender.get(0)
                        benilist=benilist+"<Ben_details><Name>"+person.beniname+"</Name><Age>"+person.age+"</Age><Gender>"+ ch.uppercaseChar().toString() +"</Gender>"+ "</Ben_details>"

                    }
                    benilist= "$benilist</NewDataSet>"

                    val BenDataXML = benilist
                    val appointMentSlot: health.data.ai.proacdoc.api.models.appointmentslots.Data =
                        spinnerAppointmentSlot.selectedItem as health.data.ai.proacdoc.api.models.appointmentslots.Data

                    var orderRequest = OrderRequest()
                    orderRequest.providerId = 1
                    orderRequest.Mobile = etMobileNo.text.toString()
                    orderRequest.userId=user.UserId
                    orderRequest.apiKey = middleWareToken
                    orderRequest.Email = user.EmailAddress
                    orderRequest.Gender = "Male"
                    orderRequest.Address = etAddress.text.toString()

                    val mString = appointMentSlot.slot!!.split("-").toTypedArray()
                    orderRequest.ApptDate = pasingDate + " " + mString[0].trim() + ":00"
                    orderRequest.Pincode = etPinCode.text.toString()

                    if (needReport.isChecked) {
                        orderRequest.Reports = "Y"
                    } else {
                        orderRequest.Reports = "N"
                    }
                    orderRequest.Product = cartList!!.joinToString (separator = ","){it.Name!!.trim() }

                    orderRequest.ReportCode =""


                    orderRequest.Rate = totalRate

                    orderRequest.BenCount = bneiList!!.size.toString()
                    orderRequest.BenDataXML = BenDataXML
                    // val jsonString = Gson().toJson(orderRequest)

                    labTestViewModel.AddOrder(orderRequest)


                }

            } else {

            }
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
                        BenificiariesListAdapter(BenificiariesListAdapter.OnItemClickListener { record ->
                            bneiList =
                                list.results.data.filterIndexed { index, engineer -> engineer.isSelected }

                            if (bneiList!!.size == 0) {
                                labTestViewModel.setBeniCount(1)

                            } else {


                                labTestViewModel.setBeniCount(bneiList!!.size)

                            }
                        }, list.results.data.toMutableList(), this)
                    binding.recyclerViewBenificiaries.adapter = postAdapter
                    binding.recyclerViewBenificiaries.itemAnimator = null;


                } else {


                }

            }
        }
        lifecycleScope.launch {
            dataStoreManager.getMiddlevareToken.collect { counter ->
                middleWareToken = counter


            }
        }
        labTestViewModel.pincodeAvaiblityResponse.observe(this, Observer {
            if (it.results.data.status.equals("Y")) {
                binding.txtpinStatus.text = it.results.data.lastMonthsOrders
                binding.txtpinStatus.visibility = View.VISIBLE
                binding.txtAppointmentTitle.visibility = View.VISIBLE
                binding.cardAppointment.visibility = View.VISIBLE


            } else {
                etPinCode.text.clear()
                binding.txtpinStatus.visibility = View.GONE
                binding.txtAppointmentTitle.visibility = View.GONE
                binding.cardAppointment.visibility = View.GONE
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
            }


        })

        labTestViewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
        loginViewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })

        labTestViewModel.appointmentSlotresponse.observe(this, Observer {
            appointmentsSlotSpinnerAdapter = AppointmentsSlotSpinnerAdapter(this, it.results.data)
            binding.spinnerAppointmentSlot.adapter = appointmentsSlotSpinnerAdapter
        })
        loginViewModel.addBeniResponse.observe(this, Observer {

            loginViewModel.getBeni(user.UserId!!.toInt())

        })

        labTestViewModel.cartItems.observe(this, Observer {
            it?.let { list ->

                cartList = it
                labTestViewModel.setAmount(list.sumOf { it.Rate!!.toInt() })


            }
        })
        labTestViewModel.getCartItems()

        labTestViewModel.totalAmount.observe(this, Observer {
            it?.let { list ->
                totalRate = it
                binding.txtTotal.text =
                    MainApp.instance.resources.getString(R.string.rs, it.toString())


            }
        })

        labTestViewModel.orderResponse.observe(this, Observer {
            if (it.results.data.respId == "RES02012") {
                Toast.makeText(applicationContext, it.results.data.response, Toast.LENGTH_SHORT)
                    .show()
                labTestViewModel.clearCart()
                finish()
            } else {
                Toast.makeText(applicationContext, it.results.data.response, Toast.LENGTH_SHORT)
                    .show()
                val builder = AlertDialog.Builder(this)
                builder.setTitle(it.results.data.response)
                builder.setMessage("Do your want to Retry?")
                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    binding.btnPlaceOrder.performClick()
                }
                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                }
                builder.show()
            }
        })


    }

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
        calendarEnd.add(Calendar.DATE, 6)


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