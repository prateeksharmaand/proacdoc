package health.care.ai.ui.labordertestsummary


import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import health.care.ai.R
import health.care.ai.api.models.User.UserModel
import health.care.ai.api.models.labordersummary.LabOrderSummaryrequest
import health.care.ai.application.MainApp
import health.care.ai.databinding.ActivityLabOrderSummaryBinding
import health.care.ai.room.entity.Cart
import health.care.ai.ui.adapters.AppointmentsSlotSpinnerAdapter
import health.care.ai.ui.adapters.BenificiariesOrderSummaryListAdapter
import health.care.ai.ui.homefragment.LabTestViewModel
import health.care.ai.ui.labordersummarypdf.LabTestOrderSummaryPDFActivity
import health.care.ai.ui.login.LoginViewModel
import health.care.ai.utils.DataStoreManager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class LabTestOrderSummaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLabOrderSummaryBinding

    private val loginViewModel: LoginViewModel by viewModel()
    private val labTestViewModel: LabTestViewModel by viewModel()

    private lateinit var user: UserModel

    private lateinit var dataStoreManager: DataStoreManager
    lateinit var appointmentsSlotSpinnerAdapter: AppointmentsSlotSpinnerAdapter
    private lateinit var middleWareToken: String


    var bneiList: List<health.care.ai.api.models.benificiary.get.Data>? = null
    var cartList: List<Cart>? = null

    var pasingDate: String? = null
    var totalRate: Int? = null
    private lateinit var postAdapter: BenificiariesOrderSummaryListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLabOrderSummaryBinding.inflate(layoutInflater)
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

    }


    fun initObserver() {

        val record = intent.extras!!.get("record") as health.care.ai.api.models.mylaborders.Data
        binding.txtOrder.text = record.orderId
        binding.txtName.text = record.product
        binding.txtApptDetails.text = record.appointmentDate
        binding.txtBooked.text = record.bookedOn
        binding.txtAmount.text =
            MainApp.instance.resources.getString(R.string.rs, record.rate.toString())
        lifecycleScope.launch {
            dataStoreManager.getMiddlevareToken.collect { counter ->
                middleWareToken = counter


            }
        }
        loginViewModel.userDetails.observe(this, Observer {
            user = it
            val orderSummaryrequest = LabOrderSummaryrequest()
            orderSummaryrequest.ApiKey = middleWareToken
            orderSummaryrequest.ProviderId = 1
            orderSummaryrequest.orderId = record.orderId
            labTestViewModel.getOrdersSummary(orderSummaryrequest)
        })
        labTestViewModel.labOrderSummaryResponse.observe(this, Observer {
            binding.txtAddress.text =
                it.results.data.orderMaster[0].address + " " + it.results.data.orderMaster[0].pincode
            linearLayoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.recyclerView.layoutManager = linearLayoutManager
            postAdapter =
                BenificiariesOrderSummaryListAdapter(BenificiariesOrderSummaryListAdapter.OnItemClickListener { record ->



                    MainApp.pdfUrl=record.url
                    val intent = Intent(this@LabTestOrderSummaryActivity, LabTestOrderSummaryPDFActivity::class.java)
                    startActivity(intent)


                }, it.results.data.benMaster.toMutableList(), this)
            binding.recyclerView.adapter = postAdapter
            binding.recyclerView.itemAnimator = null;
            binding.txtStatus.text = it.results.data.orderMaster[0].status

            when (it.results.data.orderMaster[0].status) {
                "CANCELLED" -> {

                    binding.txtStatus.setTextColor(resources.getColor(R.color.red))
                }
                "DONE", "ACCEPTED" -> {

                    binding.txtStatus.setTextColor(resources.getColor(R.color.green))
                }
                else -> {
                    binding.txtStatus.setTextColor(resources.getColor(R.color.unicorn_black))
                }
            }

        })

        labTestViewModel.loading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
        binding.llWhatsApp.setOnClickListener(View.OnClickListener {
            val toNumber = "919650269758"
            openWhatsApp(
                toNumber,
                "Need to Know about my Order status " + binding.txtOrder.text + " Booked at " + binding.txtBooked.text + " and status is " + binding.txtStatus.text + " from " + user.Name
            )

        })
        binding.llEmail.setOnClickListener(View.OnClickListener {

            with(Intent(Intent.ACTION_SEND)) {
                type = "message/rfc822"
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("proacdoc1@gmail.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Query For Lab Test")
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Need to Know about my Order status " + binding.txtOrder.text + " Booked at " + binding.txtBooked.text + " and status is " + binding.txtStatus.text + " from " + user.Name
                )
                try {
                    startActivity(Intent.createChooser(this, "Send Email with"))
                } catch (ex: ActivityNotFoundException) {
                    // No email clients found, might show Toast here
                }
            }
        })

    }

    private fun openWhatsApp(countryCode: String, mobile: String) {
        try {
            val uri = Uri.parse("https://api.whatsapp.com/send?phone=$countryCode&text=$mobile")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this@LabTestOrderSummaryActivity, "Error/n$e", Toast.LENGTH_SHORT).show()
        }
    }


}