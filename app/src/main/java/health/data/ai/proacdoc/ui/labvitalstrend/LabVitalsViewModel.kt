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

package health.data.ai.proacdoc.ui.labvitalstrend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import health.data.ai.proacdoc.api.models.customvitaldetails.AddCustomVitalsRequest
import health.data.ai.proacdoc.api.models.customvitaldetails.response.AddCustomVitalResponse
import health.data.ai.proacdoc.api.models.heartrate.AddHeartRateRequest
import health.data.ai.proacdoc.api.models.heartrate.hearrateresponse.AddHeartRateresponse
import health.data.ai.proacdoc.api.models.medicalreport.AddnewLabVital.AddLabVitalRequest
import health.data.ai.proacdoc.api.models.medicalreport.AddnewLabVital.AddLabVitalResponse
import health.data.ai.proacdoc.api.models.medicalreport.getvitalcharts.GetVitalChartsResponse
import health.data.ai.proacdoc.api.models.medicalreport.updateVitalsValue.UpdateVitalValueResponse
import health.data.ai.proacdoc.api.models.vitalsbymajorvitalid.VitalDetailsByIdResponse

import health.data.ai.proacdoc.repository.MedicalReportsRepository

import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException

class LabVitalsViewModel(
    private val medicalReportsRepository: MedicalReportsRepository,


    ) : ViewModel() {
    val errorMessage = MutableLiveData<String>()


    val vitalChartsResponse = MutableLiveData<GetVitalChartsResponse>()
    val updateVitalValueResponse = MutableLiveData<UpdateVitalValueResponse>()
    val addLabVitalResponse = MutableLiveData<AddLabVitalResponse>()
    val addHeartRateresponse = MutableLiveData<AddHeartRateresponse>()
    val VitalDetailsByIdResponse = MutableLiveData<VitalDetailsByIdResponse>()
    val addCustomVitalResponse = MutableLiveData<AddCustomVitalResponse>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->

    }
    val loading = MutableLiveData<Boolean>()


    private fun onError(message: String) {

        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }


    fun getvitalCharts(UserId: Int?, vitalId: Int?) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var post = medicalReportsRepository.GetVitalCharts(UserId, vitalId)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

                    vitalChartsResponse.postValue(post.body())


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

    fun UpdateVitalValue(mraiId: Int?, testvalue: String?, testname: String?) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var post = medicalReportsRepository.UpdateVitalValue(mraiId, testvalue, testname)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

                    updateVitalValueResponse.postValue(post.body())


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

    fun addNewLabVital(addLabVitalRequest: AddLabVitalRequest) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var post = medicalReportsRepository.addNewLabVital(addLabVitalRequest)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

                    addLabVitalResponse.postValue(post.body())


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

    fun addHeartBeatVital(addHeartRateRequest: AddHeartRateRequest) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var post = medicalReportsRepository.addNewLabVital(addHeartRateRequest)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

                    addHeartRateresponse.postValue(post.body())


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

    fun getVitalsByitalid(majorVitalId: Int?) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var post = medicalReportsRepository.getVitalsbyMajoritalId(majorVitalId)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

                    VitalDetailsByIdResponse.postValue(post.body())


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

    fun addCustomVitals(customVitalsRequest: AddCustomVitalsRequest) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var post = medicalReportsRepository.addcustomVitals(customVitalsRequest)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

                    addCustomVitalResponse.postValue(post.body())


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