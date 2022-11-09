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

package health.data.ai.proacdoc.ui.medicalrecords


import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.drjacky.imagepicker.ImagePicker
import com.github.drjacky.imagepicker.constant.ImageProvider
import health.care.ai.proacdoc.databinding.FragmentMedicalrecordsBinding
import health.data.ai.proacdoc.api.models.User.UserModel
import health.data.ai.proacdoc.api.models.medicalreport.getreportspdf.Data
import health.data.ai.proacdoc.application.MainApp

import health.data.ai.proacdoc.ui.adapters.MedicalRecordsAdapter
import health.data.ai.proacdoc.ui.login.LoginViewModel
import health.data.ai.proacdoc.ui.reportdetails.ReportDetails
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class MedicalRecordsFragment : Fragment() {
    private lateinit var binding: FragmentMedicalrecordsBinding
    private val medicalReportViewModel: MedicalReportViewModel by viewModel()
    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var file: File
    private lateinit var user: UserModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val postList = ArrayList<Data>()
    private lateinit var postAdapter: MedicalRecordsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMedicalrecordsBinding.inflate(inflater, container, false)
        initUI()
        initObserver()
        loginViewModel.checkLoggedInUserFlow();
        return binding.root

    }

    private fun initUI() {
        binding.PostNow.setOnClickListener(View.OnClickListener {

            val mimeTypes = arrayOf(

                "application/pdf",


                )

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.type = if (mimeTypes.size == 1) mimeTypes[0] else "*/*"
                if (mimeTypes.size > 0) {
                    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                }
            } else {
                var mimeTypesStr = ""
                for (mimeType in mimeTypes) {
                    mimeTypesStr += "$mimeType|"
                }
                intent.type = mimeTypesStr.substring(0, mimeTypesStr.length - 1)
            }
            val chooserIntent = Intent
                .createChooser(intent, "Choose")
            startActivityForResult(chooserIntent, 1)


        })


        binding.AddCamera.setOnClickListener(View.OnClickListener {
            ImagePicker.with(requireActivity())

                .provider(ImageProvider.BOTH).crop(9f, 16f) //Or bothCameraGallery()
                .createIntentFromDialog { launcher.launch(it) }

        })

        binding.AddPDF.setOnClickListener(View.OnClickListener {
            binding.PostNow.performClick()

        })
        linearLayoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = linearLayoutManager
        postAdapter = MedicalRecordsAdapter(MedicalRecordsAdapter.OnItemClickListener { record ->
            val intent = Intent(activity, ReportDetails::class.java)
            intent.putExtra("ago", record.ago)
            MainApp.recordId = record.recordId
            MainApp.pdfUrl = record.fileUrl
            MainApp.fileType=record.fileType
            MainApp.smartReport=record.smartReport
            startActivity(intent)


        }, postList, requireActivity())
        binding.recyclerView.adapter = postAdapter
        binding.recyclerView.itemAnimator = null;

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val file = data?.data?.let {
                getFileFromUri(requireContext().contentResolver, it, requireContext().cacheDir)
            }
            medicalReportViewModel.AddMedicalRecordPDF(file, user.UserId)
        }
    }

    private fun getFileFromUri(contentResolver: ContentResolver, uri: Uri, directory: File): File {
        val file =
            File.createTempFile("suffix", ".pdf", directory)
        file.outputStream().use {
            contentResolver.openInputStream(uri)?.copyTo(it)
        }

        return file
    }

    fun initObserver() {
        medicalReportViewModel.addMedicalPdfResponse.observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                binding.progressBar.visibility = View.GONE
                Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                medicalReportViewModel.getUserReports(user.UserId)
            }
        })









        medicalReportViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
        medicalReportViewModel.addMedicalPdfResponse.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
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

            medicalReportViewModel.getUserReports(user.UserId)
        })
        medicalReportViewModel.medicalPdfList.observe(viewLifecycleOwner) {
            it?.let { list ->
                binding.progressBar.visibility = View.GONE
                if (list.results.data.size > 0) {
                    binding.nestedRecords.visibility = View.VISIBLE
                    binding.llAddFirst.visibility = View.GONE
                    postAdapter.updateEmployeeListItems(list.results.data.toMutableList())
                } else {
                    binding.llAddFirst.visibility = View.VISIBLE
                    binding.nestedRecords.visibility = View.GONE

                }

            }
        }

    }
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!

                file = uri.toFile()
                medicalReportViewModel.AddMedicalRecordImage(file, user.UserId)
            }
        }


}

