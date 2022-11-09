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

package health.data.ai.proacdoc.ui.smarthealth


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import health.care.ai.proacdoc.databinding.FragmentSmarthealthBinding
import health.data.ai.proacdoc.api.models.User.UserModel
import health.data.ai.proacdoc.api.models.smarthealthresponse.Data

import health.data.ai.proacdoc.ui.adapters.SmartHealthAdapter
import health.data.ai.proacdoc.ui.login.LoginViewModel
import health.data.ai.proacdoc.ui.smarthealthdetails.SmartHealthDetailsActivity

import org.koin.androidx.viewmodel.ext.android.viewModel


class SmartHealthFragment : Fragment() {
    private lateinit var binding: FragmentSmarthealthBinding
    private val medicalReportViewModel: SmartHealthViewModel by viewModel()
    private val loginViewModel: LoginViewModel by viewModel()

    private lateinit var user: UserModel

    private val postList = ArrayList<Data>()
    private lateinit var postAdapter: SmartHealthAdapter
    private lateinit var linearLayoutManager: StaggeredGridLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSmarthealthBinding.inflate(inflater, container, false)

        initObserver()
        loginViewModel.checkLoggedInUserFlow();
        return binding.root

    }



    fun initObserver() {
        medicalReportViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })



        medicalReportViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })

        loginViewModel.userDetails.observe(viewLifecycleOwner, Observer {
            user = it

            medicalReportViewModel.getSmartHealthReport(user.UserId)
        })
        medicalReportViewModel.smartHealthresponse.observe(viewLifecycleOwner) {
            it?.let { list ->
                binding.progressBar.visibility = View.GONE
                if (list.results.data.size > 0) {

                    linearLayoutManager =StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    binding.recyclerView.layoutManager = linearLayoutManager
                    postAdapter = SmartHealthAdapter(SmartHealthAdapter.OnItemClickListener { record ->

                        val intent = Intent(activity, SmartHealthDetailsActivity::class.java)
                        intent.putExtra("record", record )
                        startActivity(intent)


                    }, list.results.data.toMutableList(), requireActivity())
                    binding.recyclerView.adapter = postAdapter
                    binding.recyclerView.itemAnimator = null;
                } else {


                }

            }
        }

    }
}

