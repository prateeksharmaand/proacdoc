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

package health.data.ai.proacdoc.ui.mylaborders


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import health.care.ai.proacdoc.R
import health.care.ai.proacdoc.databinding.FragmentMyOrdersBinding

import health.data.ai.proacdoc.api.models.User.UserModel

import health.data.ai.proacdoc.api.models.mylaborders.Data


import health.data.ai.proacdoc.ui.adapters.MyOrdersAdapter
import health.data.ai.proacdoc.ui.homefragment.LabTestViewModel
import health.data.ai.proacdoc.ui.labordertestsummary.LabTestOrderSummaryActivity
import health.data.ai.proacdoc.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable


class MyOrdersFragment : Fragment() {
    private lateinit var binding: FragmentMyOrdersBinding
    private val labTestViewModel: LabTestViewModel by viewModel()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val postList = ArrayList<Data>()
    private lateinit var postAdapter: MyOrdersAdapter
    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var user: UserModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyOrdersBinding.inflate(inflater, container, false)
        loginViewModel.checkLoggedInUserFlow();
        initUI()
        initObserver()

        return binding.root

    }

    private fun initUI() {
        binding.PostNow.setOnClickListener(View.OnClickListener {

            Navigation.findNavController(binding.PostNow).navigate(R.id.homeFragment);

        })

    }


    fun initObserver() {


        labTestViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })

        loginViewModel.userDetails.observe(requireActivity(), Observer {
            user = it
            labTestViewModel.getLabtestOrders(user.UserId)
        })
        labTestViewModel.labOrdersResponse.observe(requireActivity(), Observer {


            if (it.results.data.size > 0) {
                binding.nestedRecords.visibility = View.VISIBLE
                binding.llAddFirst.visibility = View.GONE
                linearLayoutManager =
                    LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                binding.recyclerView.layoutManager = linearLayoutManager
                postAdapter =
                    MyOrdersAdapter(MyOrdersAdapter.OnItemClickListener { record ->


                        val intent = Intent(activity, LabTestOrderSummaryActivity::class.java)
                        intent.putExtra("record", record as Serializable)
                        startActivity(intent)


                    }, it.results.data.toMutableList(), requireActivity())
                binding.recyclerView.adapter = postAdapter
                binding.recyclerView.itemAnimator = null;
            } else {
                binding.llAddFirst.visibility = View.VISIBLE
                binding.nestedRecords.visibility = View.GONE

            }


        })

    }


}

