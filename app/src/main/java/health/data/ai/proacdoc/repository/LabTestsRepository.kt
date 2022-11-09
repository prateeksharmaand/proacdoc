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


package health.data.ai.proacdoc.repository

import health.data.ai.proacdoc.api.PostsServices
import health.data.ai.proacdoc.api.models.Order.PostOrder.OrderRequest
import health.data.ai.proacdoc.api.models.appointmentslots.AppointmentSlotsRequest
import health.data.ai.proacdoc.api.models.labordersummary.LabOrderSummaryResponse
import health.data.ai.proacdoc.api.models.labordersummary.LabOrderSummaryrequest
import health.data.ai.proacdoc.api.models.labtest.login.ProfileTest.ProfileTestRequest
import health.data.ai.proacdoc.api.models.labtest.login.ProfileTest.ProfileTestResponse
import health.data.ai.proacdoc.api.models.pincodeavaiblity.PinCodeAvaiblityRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


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