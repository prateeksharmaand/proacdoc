/*************************************************
 * Created by Efendi Hariyadi on 04/08/22, 1:59 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 04/08/22, 1:54 PM
 ************************************************/

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

package health.data.ai.proacdoc.ui.reportdetails.smartreport


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import health.data.ai.proacdoc.api.models.medicalreport.smartreportresponse.Data
import health.data.ai.proacdoc.application.MainApp

import health.care.ai.proacdoc.databinding.FragmentMedicalrecordsmartreportBinding
import health.data.ai.proacdoc.ui.adapters.SmartReportAdapter
import health.data.ai.proacdoc.ui.labvitalstrend.ViewLabVitalsTrendActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable


class MedicalRecordSmartReportFragment : Fragment() {
    private lateinit var binding: FragmentMedicalrecordsmartreportBinding

    private val smartReportViewModel: SmartReportViewModel by viewModel()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val postList = ArrayList<Data>()
    private lateinit var postAdapter: SmartReportAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMedicalrecordsmartreportBinding.inflate(inflater, container, false)

        initObserver()
        smartReportViewModel.getSmartReport(MainApp.recordId)
        return binding.root

    }


    private fun initObserver() {
        smartReportViewModel.smartreportResponse.observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                binding.progressBar.visibility = View.GONE
                linearLayoutManager = LinearLayoutManager(activity)
                binding.recyclerView.layoutManager = linearLayoutManager
                val dividerItemDecoration = DividerItemDecoration(
                    binding.recyclerView.context,
                    linearLayoutManager.orientation
                )
                binding.recyclerView.addItemDecoration(dividerItemDecoration)
                postAdapter = SmartReportAdapter(SmartReportAdapter.OnItemClickListener { vitalData ->


                    val intent = Intent(activity, ViewLabVitalsTrendActivity::class.java)
                    intent.putExtra("record", vitalData as Serializable)
                    startActivity(intent)
                    requireActivity().finish()

                }, list.results.data.toMutableList(), requireActivity())
                binding.recyclerView.adapter = postAdapter
                binding.recyclerView.itemAnimator = null;


            }
        })










        smartReportViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })



        smartReportViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })





    }

}

