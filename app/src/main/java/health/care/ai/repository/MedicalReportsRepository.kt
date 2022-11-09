/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 05/07/22, 1:26 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 05/07/22, 1:26 PM
 ************************************************/

package health.care.ai.repository

import health.care.ai.api.PostsServices
import health.care.ai.api.models.customvitaldetails.AddCustomVitalsRequest
import health.care.ai.api.models.customvitaldetails.response.AddCustomVitalResponse
import health.care.ai.api.models.heartrate.AddHeartRateRequest
import health.care.ai.api.models.medicalreport.AddnewLabVital.AddLabVitalRequest
import health.care.ai.api.models.medicalreport.getreportspdf.MedicalReportPDFResponse
import health.care.ai.api.models.vitalsbymajorvitalid.VitalDetailsByIdResponse

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Path

class MedicalReportsRepository(private val postsServices: PostsServices) {


    suspend fun AddMedicalRecordPDF(file: MultipartBody.Part, UserId: RequestBody) =
        postsServices.AddMedicalRecordPDF(file, UserId)

    suspend fun AddMedicalRecordImage(file: MultipartBody.Part, UserId: RequestBody) =
        postsServices.AddMedicalRecordImage(file, UserId)


    fun getallMedicalPDFS(UserId: Int?): Flow<MedicalReportPDFResponse> {

        val medicalPdfDocuments: Flow<MedicalReportPDFResponse> = flow {
            while (true) {
                val latestNews = postsServices.GetallPDFS(UserId)
                emit(latestNews) // Emits the result of the request to the flow
                delay(5000) // Suspends the coroutine for some time
            }
        }
        return medicalPdfDocuments
    }

    suspend fun GetSmartReport(recordId: Int?) = postsServices.GetSmartReport(recordId)


    suspend fun GetVitalCharts(UserId: Int?, vitalId: Int?) =
        postsServices.GetVitalCharts(UserId, vitalId)

    suspend fun UpdateVitalValue(mraiId: Int?, testvalue: String?, testname: String?) =
        postsServices.UpdateVitalValue(mraiId, testvalue, testname)


    suspend fun addNewLabVital(addLabVitalRequest: AddLabVitalRequest) =
        postsServices.addNewLabVital(addLabVitalRequest)


    suspend fun addNewLabVital(addHeartRateRequest: AddHeartRateRequest) =
        postsServices.addHeartRate(addHeartRateRequest)


    suspend fun getVitalsbyMajoritalId(majorVitalId: Int?) =
        postsServices.getVitalsbyMajoritalId(majorVitalId)


    suspend fun addcustomVitals(addCustomVitalsRequest: AddCustomVitalsRequest) =
        postsServices.addcustomVitals(addCustomVitalsRequest)

}