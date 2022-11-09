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

package health.data.ai.proacdoc.ui.reportdetails.smartreport

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import health.data.ai.proacdoc.api.models.medicalreport.smartreportresponse.SmartreportResponse

import health.data.ai.proacdoc.repository.MedicalReportsRepository

import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException

class SmartReportViewModel(
    private val medicalReportsRepository: MedicalReportsRepository,


) : ViewModel() {
    val errorMessage = MutableLiveData<String>()

    val smartreportResponse = MutableLiveData<SmartreportResponse>()
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




    fun getSmartReport(recordId: Int) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var post = medicalReportsRepository.GetSmartReport(recordId)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

                    smartreportResponse.postValue(post.body())


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