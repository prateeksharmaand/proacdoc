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

package health.data.ai.proacdoc.ui.abhaconsentdetails


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import health.care.ai.proacdoc.databinding.FragmentAbhaConsentDetailsBinding
import health.data.ai.proacdoc.api.models.abhaconsentdetails.LocalConsentRequest
import health.data.ai.proacdoc.api.models.abhausertoken.AbhaUserTokenRequest
import health.data.ai.proacdoc.ui.adapters.ConsentListItemAdapter
import health.data.ai.proacdoc.ui.createabhaaadharactivity.AbhaViewModel
import health.data.ai.proacdoc.ui.login.LoginViewModel
import health.data.ai.proacdoc.utils.DataStoreManager
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AbhaConsentgrantedFragment : Fragment() {
    private lateinit var binding: FragmentAbhaConsentDetailsBinding
    private val loginViewModel: LoginViewModel by viewModel()
    private val abhaViewModel: AbhaViewModel by viewModel()
    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var abhaToken: String
    private lateinit var txnId: String
    private var beniid: Int = 0
    private lateinit var postAdapter: ConsentListItemAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    val concentRequests = ArrayList<LocalConsentRequest>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAbhaConsentDetailsBinding.inflate(inflater, container, false)
        initUI()

        dataStoreManager = DataStoreManager(requireActivity())




        initObserver()
        loginViewModel.getAbhaToken()



        return binding.root

    }

    fun initUI() {


    }

    fun initObserver() {


        loginViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.llLoading.visibility = View.VISIBLE
            } else {
                binding.llLoading.visibility = View.GONE
            }
        }
        abhaViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.llLoading.visibility = View.VISIBLE
            } else {
                binding.llLoading.visibility = View.GONE
            }
        }
        loginViewModel.abhatoken.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                abhaToken = it.results.data.accessToken
                dataStoreManager.setAbhaToken(it.results.data.accessToken)
                dataStoreManager.getbeniId.collect { counter ->
                    beniid = counter
                    loginViewModel.getBeniDetails(counter)


                }

            }
        }
        loginViewModel.benificiaryListResponse.observe(viewLifecycleOwner) {
            try {
                binding.llLoading.visibility = View.GONE

            } catch (e: Exception) {
            }


            val abhaUserTokenRequest = AbhaUserTokenRequest()
            abhaUserTokenRequest.refreshToken = it.results.data[0].refreshToken
            abhaViewModel.getAbhaUserToken(abhaUserTokenRequest, "Bearer $abhaToken")


        }
        abhaViewModel.getabhaUserTokenresponse.observe(viewLifecycleOwner) {
            abhaViewModel.getConsentsByTypeGranted(
                "GRANTED",
                "Bearer $abhaToken",
                "Bearer ${it.accessToken}"
            )

        }
        abhaViewModel.abhaConsentGrantedListDetailsresponse.observe(viewLifecycleOwner) {


            if (it.authorizations.size == 0 && it.consents.size == 0 && it.subscriptions.size == 0 && it.lockerSetups.size == 0) {


            } else {

                if (it.consents.size > 0) {
                    val localConsentRequest = LocalConsentRequest()
                    for (item in it.consents.requests) {
                        localConsentRequest.Providername = item.requester.name
                        localConsentRequest.RequestType = "Consent"
                        localConsentRequest.Perpose = item.purpose.text
                        localConsentRequest.Status = item.status

                        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                        val outputFormat = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
                        val parsedDate: Date = inputFormat.parse(item.permission.dataEraseAt)
                        val formattedDate: String = outputFormat.format(parsedDate)




                        localConsentRequest.Period = formattedDate
                        concentRequests.add(localConsentRequest)
                    }
                }
                if (it.lockerSetups.size > 0) {
                    val localConsentRequest = LocalConsentRequest()
                    for (item in it.lockerSetups.requests) {
                        localConsentRequest.Providername = item.authorization.requester.name
                        localConsentRequest.RequestType = "Health Locker Access"
                        localConsentRequest.Perpose = item.authorization.purpose.text
                        localConsentRequest.Status = item.authorization.status

                        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                        val outputFormat = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
                        val parsedDate: Date = inputFormat.parse(item.subscription.period.to)
                        val formattedDate: String = outputFormat.format(parsedDate)




                        localConsentRequest.Period = formattedDate
                        concentRequests.add(localConsentRequest)
                    }
                }
                if (it.subscriptions.size > 0) {
                    val localConsentRequest = LocalConsentRequest()
                    for (item in it.subscriptions.requests) {
                        localConsentRequest.Providername = item.hiu.name
                        localConsentRequest.RequestType = "Subscriptions"
                        localConsentRequest.Perpose = item.purpose.text
                        localConsentRequest.Status = item.status

                        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                        val outputFormat = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
                        val parsedDate: Date = inputFormat.parse(item.period.to)
                        val formattedDate: String = outputFormat.format(parsedDate)




                        localConsentRequest.Period = formattedDate
                        concentRequests.add(localConsentRequest)
                    }
                }
                if (it.authorizations.size > 0) {
                    val localConsentRequest = LocalConsentRequest()
                    for (item in it.authorizations.requests) {
                        localConsentRequest.Providername = item.requester.name
                        localConsentRequest.RequestType = "Authorization"
                        localConsentRequest.Perpose = item.purpose.text
                        localConsentRequest.Status = item.status
                        localConsentRequest.Period = "Till Approved"
                        concentRequests.add(localConsentRequest)
                    }
                }


                linearLayoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.rvConsent.layoutManager = linearLayoutManager
                postAdapter =
                    ConsentListItemAdapter(ConsentListItemAdapter.OnItemClickListener { record ->


                    }, concentRequests.toMutableList(), requireActivity())
                binding.rvConsent.adapter = postAdapter
                binding.rvConsent.itemAnimator = null;

            }
        }


    }


}

