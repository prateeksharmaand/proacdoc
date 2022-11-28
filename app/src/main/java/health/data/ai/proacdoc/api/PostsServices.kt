/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 4:17 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 4:17 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 11/06/22, 3:28 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 11/06/22, 3:28 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 6/18/20 11:20 PM
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 6/18/20 11:20 PM
 ************************************************/

package health.data.ai.proacdoc.api


import health.care.ai.proacdoc.BuildConfig
import health.data.ai.proacdoc.api.models.Order.PostOrder.OrderRequest
import health.data.ai.proacdoc.api.models.Order.PostOrder.OrderResponse
import health.data.ai.proacdoc.api.models.abhaconsentdetails.AbhaConsentListResponse
import health.data.ai.proacdoc.api.models.abhatoken.AbhaTokenModel
import health.data.ai.proacdoc.api.models.abhausertoken.AbhaUserTokenRequest
import health.data.ai.proacdoc.api.models.abhausertoken.AbhaUserTokenresponse
import health.data.ai.proacdoc.api.models.addAbhaToProfile.AddAbhaToProfileRequest
import health.data.ai.proacdoc.api.models.addAbhaToProfile.AddAbhaToProfileResponse
import health.data.ai.proacdoc.api.models.appointmentslots.AppointmentSlotresponse
import health.data.ai.proacdoc.api.models.appointmentslots.AppointmentSlotsRequest
import health.data.ai.proacdoc.api.models.benificiary.add.AddBeniRequest
import health.data.ai.proacdoc.api.models.benificiary.add.AddBeniResponse
import health.data.ai.proacdoc.api.models.benificiary.get.BenificiaryListResponse
import health.data.ai.proacdoc.api.models.customvitaldetails.AddCustomVitalsRequest
import health.data.ai.proacdoc.api.models.customvitaldetails.response.AddCustomVitalResponse
import health.data.ai.proacdoc.api.models.heartrate.AddHeartRateRequest
import health.data.ai.proacdoc.api.models.heartrate.hearrateresponse.AddHeartRateresponse
import health.data.ai.proacdoc.api.models.labordersummary.LabOrderSummaryResponse
import health.data.ai.proacdoc.api.models.labordersummary.LabOrderSummaryrequest
import health.data.ai.proacdoc.api.models.labtest.login.LabTestLoginRespone
import health.data.ai.proacdoc.api.models.labtest.login.ProfileTest.ProfileTestRequest
import health.data.ai.proacdoc.api.models.labtest.login.ProfileTest.ProfileTestResponse
import health.data.ai.proacdoc.api.models.login.profile.UpdateProfileRequest
import health.data.ai.proacdoc.api.models.login.profile.UpdateProfileResponse
import health.data.ai.proacdoc.api.models.medicalreport.AddnewLabVital.AddLabVitalRequest
import health.data.ai.proacdoc.api.models.medicalreport.AddnewLabVital.AddLabVitalResponse
import health.data.ai.proacdoc.api.models.medicalreport.addpdf.AddMedicalRepordPdfResponse
import health.data.ai.proacdoc.api.models.medicalreport.getreportspdf.MedicalReportPDFResponse
import health.data.ai.proacdoc.api.models.medicalreport.getvitalcharts.GetVitalChartsResponse
import health.data.ai.proacdoc.api.models.medicalreport.smartreportresponse.SmartreportResponse
import health.data.ai.proacdoc.api.models.medicalreport.updateVitalsValue.UpdateVitalValueResponse
import health.data.ai.proacdoc.api.models.mylaborders.LabOrdersResponse
import health.data.ai.proacdoc.api.models.pincodeavaiblity.PinCodeAvaiblityRequest
import health.data.ai.proacdoc.api.models.pincodeavaiblity.PincodeAvaiblityResponse
import health.data.ai.proacdoc.api.models.registerabha.RegisterAbhaRequest
import health.data.ai.proacdoc.api.models.registerabha.RegisterAbhaResponse
import health.data.ai.proacdoc.api.models.registerabhagenerateadharotp.RegisterAbhaGenerateAdharOtpRequest
import health.data.ai.proacdoc.api.models.registerabhagenerateadharotp.RegisterAbhaRequestAdharOtpResponse
import health.data.ai.proacdoc.api.models.registerabhageneratemobileotp.RegisterAbhaGenerateMobileOtp
import health.data.ai.proacdoc.api.models.registerabhageneratemobileotp.RegisterabhaGenerateMobileOtpResponse
import health.data.ai.proacdoc.api.models.registerabhaverifymobileotp.RegisterAdharVerifyMobileOtpResponse
import health.data.ai.proacdoc.api.models.registerabhaverifymobileotp.RegisterabhaVerifyMobileOtpRequest
import health.data.ai.proacdoc.api.models.smarthealthresponse.SmartHealthresponse
import health.data.ai.proacdoc.api.models.updatephoneno.UpdatePhoneNoResponse
import health.data.ai.proacdoc.api.models.verifyreisteraadharotp.VerifyAbhaRegisterAadharOtpResponse
import health.data.ai.proacdoc.api.models.verifyreisteraadharotp.VerifyRegisterAAadharOtpRequest
import health.data.ai.proacdoc.api.models.vitalsbymajorvitalid.VitalDetailsByIdResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


interface PostsServices {




    @Headers("Accept: application/json")
    @POST("api/user/post")
    suspend fun LoginUser(@Body userModel: health.data.ai.proacdoc.api.models.User.UserModel): Response<health.data.ai.proacdoc.api.models.login.LoginResponse>


    @Multipart
    @POST("/api/fileupload")
    suspend fun AddMedicalRecordPDF(
        @Part file: MultipartBody.Part,
        @Part("userId") UserId: RequestBody?,
    ): Response<AddMedicalRepordPdfResponse>


    @Multipart
    @POST("/api/fileuploadImage")
    suspend fun AddMedicalRecordImage(
        @Part file: MultipartBody.Part,
        @Part("userId") UserId: RequestBody?,
    ): Response<AddMedicalRepordPdfResponse>




    @Headers("Accept: application/json")
    @GET("api/User/UpdateToken/{UserId}/{Token}")
    suspend fun UpdateNotification(
        @Path("UserId") UserId: Int?,
        @Path("Token") Latitude: String

    ): Long



    @Headers("Accept: application/json")
    @GET("api/User/UpdateMobile/{UserId}/{mobile}")
    suspend fun UpdatePhoneNumber(
        @Path("UserId") UserId: Int?,
        @Path("mobile") mobile: String

    ): Response<UpdatePhoneNoResponse>



    @POST("/api/User/UpdateProfile")
    suspend fun UpdateProfile(@Body updateProfileRequest: UpdateProfileRequest): Response<UpdateProfileResponse>

    @Headers("Accept: application/json")
    @GET("api/medicalreport/GetReport/{userId}")
    suspend fun GetallPDFS(@Path("userId") UserId: Int?, ): MedicalReportPDFResponse


    @Headers("Accept: application/json")
    @GET("api/medicalreport/GetSmartReport/{recordId}")
    suspend fun GetSmartReport(@Path("recordId") recordId: Int?, ):Response<SmartreportResponse>



    @Headers("Accept: application/json")
    @GET("api/vitaldetails/getcharts/{userId}/{vitalId}")
    suspend fun GetVitalCharts(@Path("userId") UserId: Int?,@Path("vitalId") vitalId: Int?, ):Response<GetVitalChartsResponse>

    @Headers("Accept: application/json")
    @GET("api/vitaldetails/updateVitalValue/{mraiId}/{testvalue}/{testname}")
    suspend fun UpdateVitalValue(@Path("mraiId") mraiId: Int?,@Path("testvalue") testvalue: String?,@Path("testname") testname: String?, ):Response<UpdateVitalValueResponse>


    @POST("/api/vitaldetails/addnewLabVital")
    suspend fun addNewLabVital(@Body addLabVitalRequest: AddLabVitalRequest): Response<AddLabVitalResponse>


    @GET("/api/smartHealth/GetSmartHealthAnalysis/{userId}")
    suspend fun getsmarthealthdetails(@Path("userId") userId: Int?, ): Response<SmartHealthresponse>


    @POST("/api/vitaldetails/addHeartRate")
    suspend fun addHeartRate(@Body addHeartRateRequest: AddHeartRateRequest): Response<AddHeartRateresponse>

    @Headers("Accept: application/json")
    @GET("/api/getvitals/getvitalsByMajorVitalId/{majorVitalId}")
    suspend fun getVitalsbyMajoritalId(@Path("majorVitalId") majorVitalId: Int?,): Response<VitalDetailsByIdResponse>



    @POST("/api/vitaldetails/addCustomVitalRecords")
    suspend fun addcustomVitals(@Body addCustomVitalsRequest: AddCustomVitalsRequest): Response<AddCustomVitalResponse>




    @Headers("Accept: application/json")
    @GET("/api/labtest/getProviderToken/{providerId}")
    suspend fun getLabTestProviderLogin(@Path("providerId") providerId: Int?,): Response<LabTestLoginRespone>

    @POST("/api/labtest/gettests")
    suspend fun GetProfileTests(@Body profileTestRequest: ProfileTestRequest): ProfileTestResponse



    @POST("/api/labtest/getAppontmentSlots")
    suspend fun getAppointmentSlots(@Body appointmentSlotsRequest: AppointmentSlotsRequest): Response<AppointmentSlotresponse>

    @POST("/api/labtest/verifyPinCodeAvaiblity")
    suspend fun verifyPinCodeAvaiblity(@Body pinCodeAvaiblityRequest: PinCodeAvaiblityRequest): Response<PincodeAvaiblityResponse>


    @Headers("Accept: application/json")
    @GET("/api/labtest/Getbeni/{beniUserId}")
    suspend fun getBeni(@Path("beniUserId") beniUserId: Int?,): Response<BenificiaryListResponse>


    @POST("/api/labtest/Addbeni")
    suspend fun Addbeni(@Body addBeniRequest: AddBeniRequest): Response<AddBeniResponse>

    @POST("/api/labtest/bookLabTest")
    suspend fun AddOrder(@Body orderRequest: OrderRequest): Response<OrderResponse>


    @Headers("Accept: application/json")
    @GET("/api/labtest/getOrders/{userId}")
    suspend fun getLabtestOrders(@Path("userId") userId: Int?,): Response<LabOrdersResponse>


    @POST("/api/labtest/getOrdersSummary")
    suspend fun getOrdersSummary(@Body labOrderSummaryrequest: LabOrderSummaryrequest): LabOrderSummaryResponse


    @Headers("Accept: application/json")
    @POST("/api/abha/getToken")
    suspend fun getAbhaToken(): Response<AbhaTokenModel>


    @Headers("Accept: application/json")
    @POST(BuildConfig.registration_aadhaar_generateOtp)
    suspend fun generateRegisterAdharOtp(@Body registerAbhaGenerateAdharOtpRequest: RegisterAbhaGenerateAdharOtpRequest, @Header("Authorization") token:String): Response<RegisterAbhaRequestAdharOtpResponse>

    @Headers("Accept: application/json")
    @POST(BuildConfig.registration_aadhaar_verifyOtp)
    suspend fun verifyRegisterAdharOtp(@Body verifyRegisterAAadharOtpRequest: VerifyRegisterAAadharOtpRequest, @Header("Authorization") token:String): Response<VerifyAbhaRegisterAadharOtpResponse>



    @Headers("Accept: application/json")
    @POST(BuildConfig.registration_aadhaar_generateMobileOtp)
    suspend fun generateRegisterMobileOtp(@Body registerAbhaGenerateMobileOtp: RegisterAbhaGenerateMobileOtp, @Header("Authorization") token:String): Response<RegisterabhaGenerateMobileOtpResponse>

    @Headers("Accept: application/json")
    @POST(BuildConfig.registration_aadhaar_verifyMobileOtp)
    suspend fun verifyRegisterMobileOtp(@Body registerabhaVerifyMobileOtpRequest: RegisterabhaVerifyMobileOtpRequest, @Header("Authorization") token:String): Response<RegisterAdharVerifyMobileOtpResponse>

    @Headers("Accept: application/json")
    @POST(BuildConfig.registration_Create_AbhaId)
    suspend fun createAbhaId(@Body registerAbhaRequest: RegisterAbhaRequest, @Header("Authorization") token:String): Response<RegisterAbhaResponse>

    @Headers("Accept: application/json")
    @GET("/api/labtest/GetbeniDetails/{baniid}")
    suspend fun getBeniDetails(@Path("baniid") baniid: Int?,): Response<BenificiaryListResponse>



    @POST("/api/addAbhaToBeni")
    suspend fun addAbhaToBeni(
       @Body addAbhaToProfileRequest: AddAbhaToProfileRequest

    ): Response<AddAbhaToProfileResponse>


    @Headers("Accept: application/json")
    @POST(BuildConfig.abha_getuserToken)
    suspend fun getAbhaUserToken(@Body abhaUserTokenRequest: AbhaUserTokenRequest, @Header("Authorization") token:String): Response<AbhaUserTokenresponse>

    @Headers("Accept: application/json")
    @GET(BuildConfig.abha_getQr)
    suspend fun GetAbhaUserQr( @Header("Authorization") token:String,@Header("X-Token") xToken:String,): Response<ResponseBody>

    @Headers("Accept: application/json")
    @GET(BuildConfig.abha_getAbhaCard)
    suspend fun GetAbhaUserCard( @Header("Authorization") token:String,@Header("X-Token") xToken:String,): Response<ResponseBody>


    @Headers("Accept: application/json")
    @GET(BuildConfig.abha_getConsent_by_type)
    suspend fun getConsentsByType(@Query("status") status:String, @Header("Authorization") token:String,@Header("X-Auth-Token") xToken:String,): Response<AbhaConsentListResponse>





}