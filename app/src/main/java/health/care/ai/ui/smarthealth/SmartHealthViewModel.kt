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

package health.care.ai.ui.smarthealth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import health.care.ai.api.models.smarthealthresponse.SmartHealthresponse

import health.care.ai.repository.SmartHealthRepository

import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException

class SmartHealthViewModel(
    private val smartHealthRepository: SmartHealthRepository,


    ) : ViewModel() {
    val errorMessage = MutableLiveData<String>()

    val smartHealthresponse = MutableLiveData<SmartHealthresponse>()

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


    fun getSmartHealthReport(userId: Int?) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var post = smartHealthRepository.getsmarthealthdetails(userId)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

                    smartHealthresponse.postValue(post.body())


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