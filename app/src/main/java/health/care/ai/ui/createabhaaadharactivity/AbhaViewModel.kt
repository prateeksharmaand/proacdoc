/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 01/07/22, 10:37 AM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 01/07/22, 10:37 AM
 ************************************************/

package health.care.ai.ui.createabhaaadharactivity

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import health.care.ai.BuildConfig
import health.care.ai.api.models.addAbhaToProfile.AddAbhaToProfileRequest
import health.care.ai.api.models.addAbhaToProfile.AddAbhaToProfileResponse
import health.care.ai.api.models.medicalreport.addpdf.AddMedicalRepordPdfResponse
import health.care.ai.api.models.medicalreport.getreportspdf.MedicalReportPDFResponse
import health.care.ai.api.models.registerabha.AbhaExceptionResponse
import health.care.ai.api.models.registerabha.RegisterAbhaRequest
import health.care.ai.api.models.registerabha.RegisterAbhaResponse
import health.care.ai.api.models.registerabhagenerateadharotp.RegisterAbhaGenerateAdharOtpRequest
import health.care.ai.api.models.registerabhagenerateadharotp.RegisterAbhaRequestAdharOtpResponse
import health.care.ai.api.models.registerabhageneratemobileotp.RegisterAbhaGenerateMobileOtp
import health.care.ai.api.models.registerabhageneratemobileotp.RegisterabhaGenerateMobileOtpResponse
import health.care.ai.api.models.registerabhaverifymobileotp.RegisterAdharVerifyMobileOtpResponse
import health.care.ai.api.models.registerabhaverifymobileotp.RegisterabhaVerifyMobileOtpRequest
import health.care.ai.api.models.verifyreisteraadharotp.VerifyAbhaRegisterAadharOtpResponse
import health.care.ai.api.models.verifyreisteraadharotp.VerifyRegisterAAadharOtpRequest
import health.care.ai.repository.AbhaRepository

import health.care.ai.repository.MedicalReportsRepository

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import kotlinx.coroutines.flow.collect
import retrofit2.http.Body
import retrofit2.http.Header

class AbhaViewModel(
    private val abhaRepository: AbhaRepository,


    ) : ViewModel() {
    val errorMessage = MutableLiveData<String>()

    val abhaRequestAdharOtpResponse = MutableLiveData<RegisterAbhaRequestAdharOtpResponse>()
    val verifyAbhaRegisterAadharOtpResponse = MutableLiveData<VerifyAbhaRegisterAadharOtpResponse>()
    val registerabhaGenerateMobileOtpResponse =
        MutableLiveData<RegisterabhaGenerateMobileOtpResponse>()
    val registerAdharVerifyMobileOtpResponse =
        MutableLiveData<RegisterAdharVerifyMobileOtpResponse>()
    val registerAbhaResponse =
        MutableLiveData<RegisterAbhaResponse>()
    val registererrorAbhaResponse =
        MutableLiveData<AbhaExceptionResponse>()
    val addAbhaToProfileResponse =
        MutableLiveData<AddAbhaToProfileResponse>()
    var job: Job? = null

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->

    }
    val loading = MutableLiveData<Boolean>()


    fun abhaRequestAdharOtp(
        registerAbhaGenerateAdharOtpRequest: RegisterAbhaGenerateAdharOtpRequest,
        token: String
    ) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val post =
                abhaRepository.generateRegisterAdharOtp(registerAbhaGenerateAdharOtpRequest, token)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

                    abhaRequestAdharOtpResponse.postValue(post.body())


                } catch (throwable: Throwable) {
                    loading.postValue(false)
                    when (throwable) {
                        is IOException -> {
                            onError("Network Error")
                        }
                        is HttpException -> {
                            val codeError = throwable.code()
                            val errorMessageResponse = throwable.message()
                            onError("Error $errorMessageResponse : $codeError")
                        }
                        else -> {
                            onError("UnKnown error")
                        }
                    }
                }
                loading.value = false
            }
        }

    }


    fun verifyabhaRequestAdharOtp(
        verifyRegisterAAadharOtpRequest: VerifyRegisterAAadharOtpRequest,
        token: String
    ) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val post = abhaRepository.verifyRegisterAdharOtp(verifyRegisterAAadharOtpRequest, token)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

                    verifyAbhaRegisterAadharOtpResponse.postValue(post.body())


                } catch (throwable: Throwable) {
                    loading.postValue(false)
                    when (throwable) {
                        is IOException -> {
                            onError("Network Error")
                        }
                        is HttpException -> {
                            val codeError = throwable.code()
                            val errorMessageResponse = throwable.message()
                            onError("Error $errorMessageResponse : $codeError")
                        }
                        else -> {
                            onError("UnKnown error")
                        }
                    }
                }
                loading.value = false
            }
        }

    }


    fun abhaRequestMobileOtp(
        registerAbhaGenerateMobileOtp: RegisterAbhaGenerateMobileOtp,
        token: String
    ) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val post =
                abhaRepository.generateRegisterMobileOtp(registerAbhaGenerateMobileOtp, token)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

                    registerabhaGenerateMobileOtpResponse.postValue(post.body())


                } catch (throwable: Throwable) {
                    loading.postValue(false)
                    when (throwable) {
                        is IOException -> {
                            onError("Network Error")
                        }
                        is HttpException -> {
                            val codeError = throwable.code()
                            val errorMessageResponse = throwable.message()
                            onError("Error $errorMessageResponse : $codeError")
                        }
                        else -> {
                            onError("UnKnown error")
                        }
                    }
                }
                loading.value = false
            }
        }

    }

    fun abharegisterverifyMobileOtp(
        registerabhaVerifyMobileOtpRequest: RegisterabhaVerifyMobileOtpRequest,
        token: String
    ) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val post =
                abhaRepository.verifyRegisterMobileOtp(registerabhaVerifyMobileOtpRequest, token)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

                    registerAdharVerifyMobileOtpResponse.postValue(post.body())


                } catch (throwable: Throwable) {
                    loading.postValue(false)
                    when (throwable) {
                        is IOException -> {
                            onError("Network Error")
                        }
                        is HttpException -> {
                            val codeError = throwable.code()
                            val errorMessageResponse = throwable.message()
                            onError("Error $errorMessageResponse : $codeError")
                        }
                        else -> {
                            onError("UnKnown error")
                        }
                    }
                }
                loading.value = false
            }
        }

    }

    fun createAbhaId(
        registerAbhaRequest: RegisterAbhaRequest,
        token: String
    ) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val post =
                abhaRepository.createAbhaId(registerAbhaRequest, token)

            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

                    if (post.code() == 200) {
                        registerAbhaResponse.postValue(post.body())
                    } else if (post.code() == 400) {
                        val gson = Gson()
                        val type = object : TypeToken<AbhaExceptionResponse>() {}.type
                        var errorResponse: AbhaExceptionResponse? = gson.fromJson(post.errorBody()!!.charStream(), type)
                        registererrorAbhaResponse.postValue(errorResponse!!)
                    } else {

                    }


                } catch (throwable: Throwable) {
                    loading.postValue(false)
                    when (throwable) {
                        is IOException -> {
                            //    onError("Network Error")
                        }
                        is HttpException -> {
                            val codeError = throwable.code()
                            val errorMessageResponse = throwable.message()
                            onError("Error $errorMessageResponse : $codeError")
                        }
                        else -> {
                            ////onError("UnKnown error")
                        }
                    }
                }
                loading.value = false
            }
        }

    }

    private fun onError(message: String) {

        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }




    fun addAbhaToBeni( registerAbhaRequest : AddAbhaToProfileRequest) {

        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {



            val result = abhaRepository.addAbhaToBeni(registerAbhaRequest)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)


                    addAbhaToProfileResponse.postValue(result.body())
                } catch (throwable: Throwable) {
                    loading.postValue(false)
                    when (throwable) {
                        is IOException -> {
                            onError("Network Error")
                        }
                        is HttpException -> {
                            val codeError = throwable.code()
                            val errorMessageResponse = throwable.message()
                            onError("Error $errorMessageResponse : $codeError")
                        }
                        else -> {
                            onError("UnKnown error")
                        }
                    }
                }
                loading.value = false
            }
        }

    }
}