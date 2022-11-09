/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 4:18 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 4:18 PM
 ************************************************/


package health.care.ai.repository

import health.care.ai.api.PostsServices
import health.care.ai.api.models.Order.PostOrder.OrderRequest
import health.care.ai.api.models.Order.PostOrder.OrderResponse
import health.care.ai.api.models.appointmentslots.AppointmentSlotresponse
import health.care.ai.api.models.appointmentslots.AppointmentSlotsRequest
import health.care.ai.api.models.customvitaldetails.AddCustomVitalsRequest
import health.care.ai.api.models.labordersummary.LabOrderSummaryResponse
import health.care.ai.api.models.labordersummary.LabOrderSummaryrequest
import health.care.ai.api.models.labtest.login.ProfileTest.ProfileTestRequest
import health.care.ai.api.models.labtest.login.ProfileTest.ProfileTestResponse
import health.care.ai.api.models.medicalreport.getreportspdf.MedicalReportPDFResponse
import health.care.ai.api.models.mylaborders.LabOrdersResponse
import health.care.ai.api.models.pincodeavaiblity.PinCodeAvaiblityRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path


class LabTestsRepository(private val postsServices: PostsServices) {
    suspend fun getLabTestProviderLogin(providerId: Int?) =
        postsServices.getLabTestProviderLogin(providerId)


    fun getProfileTests(profileTestRequest: ProfileTestRequest): Flow<ProfileTestResponse> {

        val testsLists: Flow<ProfileTestResponse> = flow {
            while (true) {
                val latestLists = postsServices.GetProfileTests(profileTestRequest)
                emit(latestLists)
                delay(10000)
            }
        }
        return testsLists
    }

    suspend fun getAppointmentSlots(appointmentSlotsRequest: AppointmentSlotsRequest) =
        postsServices.getAppointmentSlots(appointmentSlotsRequest)


    suspend fun verifyPinCodeAvaiblity(pinCodeAvaiblityRequest: PinCodeAvaiblityRequest) =
        postsServices.verifyPinCodeAvaiblity(pinCodeAvaiblityRequest)


    suspend fun AddOrder(orderRequest: OrderRequest) =
        postsServices.AddOrder(orderRequest)

    suspend fun getLabtestOrders(userId: Int?) =
        postsServices.getLabtestOrders(userId)


    fun getOrdersSummary(labOrderSummaryrequest: LabOrderSummaryrequest): Flow<LabOrderSummaryResponse> {

        val testsLists: Flow<LabOrderSummaryResponse> = flow {
            while (true) {
                val latestLists = postsServices.getOrdersSummary(labOrderSummaryrequest)
                emit(latestLists)
                delay(10000)
            }
        }
        return testsLists
    }


}