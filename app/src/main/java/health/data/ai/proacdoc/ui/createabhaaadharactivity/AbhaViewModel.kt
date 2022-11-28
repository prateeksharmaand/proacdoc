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

package health.data.ai.proacdoc.ui.createabhaaadharactivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import health.data.ai.proacdoc.api.models.abhaconsentdetails.AbhaConsentListResponse
import health.data.ai.proacdoc.api.models.abhausertoken.AbhaUserTokenRequest
import health.data.ai.proacdoc.api.models.abhausertoken.AbhaUserTokenresponse
import health.data.ai.proacdoc.api.models.addAbhaToProfile.AddAbhaToProfileRequest
import health.data.ai.proacdoc.api.models.addAbhaToProfile.AddAbhaToProfileResponse
import health.data.ai.proacdoc.api.models.registerabha.AbhaExceptionResponse
import health.data.ai.proacdoc.api.models.registerabha.RegisterAbhaRequest
import health.data.ai.proacdoc.api.models.registerabha.RegisterAbhaResponse
import health.data.ai.proacdoc.api.models.registerabhagenerateadharotp.RegisterAbhaGenerateAdharOtpRequest
import health.data.ai.proacdoc.api.models.registerabhagenerateadharotp.RegisterAbhaRequestAdharOtpResponse
import health.data.ai.proacdoc.api.models.registerabhageneratemobileotp.RegisterAbhaGenerateMobileOtp
import health.data.ai.proacdoc.api.models.registerabhageneratemobileotp.RegisterabhaGenerateMobileOtpResponse
import health.data.ai.proacdoc.api.models.registerabhaverifymobileotp.RegisterAdharVerifyMobileOtpResponse
import health.data.ai.proacdoc.api.models.registerabhaverifymobileotp.RegisterabhaVerifyMobileOtpRequest
import health.data.ai.proacdoc.api.models.verifyreisteraadharotp.VerifyAbhaRegisterAadharOtpResponse
import health.data.ai.proacdoc.api.models.verifyreisteraadharotp.VerifyRegisterAAadharOtpRequest
import health.data.ai.proacdoc.repository.AbhaRepository

import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException

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
    val getabhaUserTokenresponse =
        MutableLiveData<AbhaUserTokenresponse>()
    val QrResponse=MutableLiveData<okhttp3.ResponseBody>()
    val abhaConsentGrantedListDetailsresponse =
        MutableLiveData<AbhaConsentListResponse>()
    val abhaConsentrequestedListDetailsresponse =
        MutableLiveData<AbhaConsentListResponse>()
    val abhaConsentDeniedListDetailsresponse =
        MutableLiveData<AbhaConsentListResponse>()
    val abhaConsentExpiredListDetailsresponse =
        MutableLiveData<AbhaConsentListResponse>()
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


    fun getAbhaUserToken(
        abhaUserTokenRequest: AbhaUserTokenRequest, token:String
    ) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val post =
                abhaRepository.getAbhaUserToken(abhaUserTokenRequest, token)

            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

                    if (post.code() == 200) {
                        getabhaUserTokenresponse.postValue(post.body())
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

    fun getAbhaUserQr(
        token:String,xToken:String
    ) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val post =
                abhaRepository.GetAbhaUserQr( token,xToken)

            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

                    if (post.code() == 200) {
                        QrResponse.postValue(post.body())
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
    fun GetAbhaUserCard(
        token:String,xToken:String
    ) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val post =
                abhaRepository.GetAbhaUserCard( token,xToken)

            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

                    if (post.code() == 200) {
                        QrResponse.postValue(post.body())
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



    fun getConsentsByTypeGranted(status:String,
        token:String,xToken:String
    ) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val post =
                abhaRepository.getConsentsByType( status,token,xToken)

            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

                    if (post.code() == 200) {
                        abhaConsentGrantedListDetailsresponse.postValue(post.body())
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
    fun getConsentsByTypeRequested(status:String,
                          token:String,xToken:String
    ) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val post =
                abhaRepository.getConsentsByType( status,token,xToken)

            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

                    if (post.code() == 200) {
                        abhaConsentrequestedListDetailsresponse.postValue(post.body())
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



    fun getConsentsByTypeDenied(status:String,
                                   token:String,xToken:String
    ) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val post =
                abhaRepository.getConsentsByType( status,token,xToken)

            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

                    if (post.code() == 200) {
                        abhaConsentDeniedListDetailsresponse.postValue(post.body())
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




    fun getConsentsByTypeExpired(status:String,
                                token:String,xToken:String
    ) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val post =
                abhaRepository.getConsentsByType( status,token,xToken)

            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

                    if (post.code() == 200) {
                        abhaConsentExpiredListDetailsresponse.postValue(post.body())
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






}