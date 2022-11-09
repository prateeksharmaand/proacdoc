/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 2:44 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 2:44 PM
 ************************************************/



package health.data.ai.proacdoc.di


import health.data.ai.proacdoc.ui.createabhaaadharactivity.AbhaViewModel
import health.data.ai.proacdoc.ui.home.HomeViewModel
import health.data.ai.proacdoc.ui.homefragment.LabTestViewModel
import health.data.ai.proacdoc.ui.labvitalstrend.LabVitalsViewModel
import health.data.ai.proacdoc.ui.login.LoginViewModel
import health.data.ai.proacdoc.ui.medicalrecords.MedicalReportViewModel
import health.data.ai.proacdoc.ui.medication.MedicineViewModel
import health.data.ai.proacdoc.ui.reportdetails.smartreport.SmartReportViewModel
import health.data.ai.proacdoc.ui.smarthealth.SmartHealthViewModel


import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {



    viewModel {  LoginViewModel(loginServerRepository = get(), postsPersistanceRepositoru = get()) }




    viewModel {  HomeViewModel(loginServerRepository=get()) }
    viewModel {  MedicalReportViewModel(get()) }
    viewModel {  SmartReportViewModel(get()) }
    viewModel {  LabVitalsViewModel(get()) }
    viewModel {  SmartHealthViewModel(get()) }
    viewModel {  LabTestViewModel(get(),get()) }

    viewModel {  MedicineViewModel(get()) }
    viewModel {  AbhaViewModel(get()) }


}

