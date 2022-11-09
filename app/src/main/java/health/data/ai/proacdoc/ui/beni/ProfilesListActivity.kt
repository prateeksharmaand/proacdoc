package health.data.ai.proacdoc.ui.beni


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import health.care.ai.proacdoc.R
import health.care.ai.proacdoc.databinding.ActivityProfilesListBinding

import health.data.ai.proacdoc.api.models.User.UserModel
import health.data.ai.proacdoc.api.models.benificiary.add.AddBeniRequest

import health.data.ai.proacdoc.ui.abhawithapi.AbhaHomeActivity
import health.data.ai.proacdoc.ui.adapters.ProfilesListAdapter
import health.data.ai.proacdoc.ui.login.LoginViewModel
import health.data.ai.proacdoc.utils.DataStoreManager
import health.data.ai.proacdoc.utils.Utils
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
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