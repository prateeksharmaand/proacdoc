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



package health.care.ai.di


import health.care.ai.ui.createabhaaadharactivity.AbhaViewModel
import health.care.ai.ui.home.HomeViewModel
import health.care.ai.ui.homefragment.LabTestViewModel
import health.care.ai.ui.labvitalstrend.LabVitalsViewModel
import health.care.ai.ui.login.LoginViewModel
import health.care.ai.ui.medicalrecords.MedicalReportViewModel
import health.care.ai.ui.medication.MedicineViewModel
import health.care.ai.ui.reportdetails.smartreport.SmartReportViewModel
import health.care.ai.ui.smarthealth.SmartHealthViewModel


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

