/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 05/07/22, 12:36 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 05/07/22, 12:36 PM
 ************************************************/


package health.data.ai.proacdoc.ui.home

import androidx.lifecycle.*
import health.data.ai.proacdoc.repository.LoginServerRepository

import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel(

    private val loginServerRepository: LoginServerRepository,

    ) : ViewModel() {
    val errorMessage = MutableLiveData<String>()


    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
       // onError("Exception handled: ${throwable.localizedMessage}")
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




    fun updateToken(userId:Int?,Token:String) {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loginServerRepository.updateToken(userId,Token)
            withContext(Dispatchers.Main) {
                try {


                    loading.value = false
                } catch (throwable: Throwable) {
                    loading.value = false
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
            }
        }

    }

}