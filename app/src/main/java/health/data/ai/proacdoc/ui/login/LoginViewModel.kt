/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 5:06 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 5:06 PM
 ************************************************/

package health.data.ai.proacdoc.ui.login

import androidx.lifecycle.*
import health.data.ai.proacdoc.api.models.User.UserModel
import health.data.ai.proacdoc.api.models.abhatoken.AbhaTokenModel
import health.data.ai.proacdoc.api.models.benificiary.add.AddBeniRequest
import health.data.ai.proacdoc.api.models.benificiary.add.AddBeniResponse
import health.data.ai.proacdoc.api.models.benificiary.get.BenificiaryListResponse
import health.data.ai.proacdoc.api.models.updatephoneno.UpdatePhoneNoResponse
import health.data.ai.proacdoc.repository.LoginServerRepository
import health.data.ai.proacdoc.repository.PostsRoomRepository

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import retrofit2.HttpException
import java.io.IOException

class LoginViewModel(
    private val loginServerRepository: LoginServerRepository,
    private val postsPersistanceRepositoru: PostsRoomRepository,


    ) : ViewModel() {


    val errorMessage = MutableLiveData<String>()
    val addressDetails = MutableLiveData<String>()
    val benificiaryListResponse = MutableLiveData<BenificiaryListResponse>()
    val addBeniResponse = MutableLiveData<AddBeniResponse>()
    val loginStatus = MutableLiveData<Boolean>()
    val updateMobileStatus = MutableLiveData<UpdatePhoneNoResponse>()
    val abhatoken = MutableLiveData<AbhaTokenModel>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    val loginResponse = MutableLiveData<health.data.ai.proacdoc.api.models.login.LoginResponse>()
    val updateProfileResponse =
        MutableLiveData<health.data.ai.proacdoc.api.models.login.profile.UpdateProfileResponse>()

    val userDetails = MutableLiveData<health.data.ai.proacdoc.api.models.User.UserModel>()
    fun checkLoginStatus() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var response = postsPersistanceRepositoru.checkisUserLoggedIn();
            withContext(Dispatchers.Main) {
                if (response.isNotEmpty()) {
                    try {


                        loginStatus.postValue(true)
                    } catch (throwable: Throwable) {
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
                } else {
                    loginStatus.postValue(false)
                }
            }
        }

    }

    fun getLoggedInUser() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var response = postsPersistanceRepositoru.checkisUserLoggedIn();
            withContext(Dispatchers.Main) {
                try {


                    userDetails.postValue(response.get(0))
                } catch (throwable: Throwable) {
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

    fun saveUser(userModel: health.data.ai.proacdoc.api.models.User.UserModel) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            postsPersistanceRepositoru.AddUser(userModel);
        }

    }


    fun loginUser(userModel: health.data.ai.proacdoc.api.models.User.UserModel) {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val result = loginServerRepository.loginUser(userModel)
            withContext(Dispatchers.Main) {
                try {

                    loginResponse.postValue(result.body())
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


    val loading = MutableLiveData<Boolean>()


    private fun onError(message: String) {
        errorMessage.value = message

        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }


    fun updateProfile(userModel: UserModel) {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val result = loginServerRepository.UpdatePhoneNumber(
                userModel.UserId,
                userModel.mobile.toString()
            )
            withContext(Dispatchers.Main) {
                try {

                    updateMobileStatus.postValue(result.body())
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

    fun updateUserProfile(userModel: health.data.ai.proacdoc.api.models.User.UserModel) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            postsPersistanceRepositoru.updateProfile(userModel);
        }

    }

    fun fullSchedule(): Flow<List<health.data.ai.proacdoc.api.models.User.UserModel>> =
        postsPersistanceRepositoru.checkisUserLoggedInFlow()

    fun checkLoggedInUserFlow() {
        loading.postValue(true)
        viewModelScope.launch {
            fullSchedule().catch { e ->
                onError(e.message.toString())

            }.onEach {
                userDetails.postValue(it[0])
                loading.postValue(false)
            }.collect()
        }

    }


    fun getBeni(beniUserId: Int) {

        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val result = loginServerRepository.getBeni(beniUserId)
            withContext(Dispatchers.Main) {
                try {

                    benificiaryListResponse.postValue(result.body())
                    loading.postValue(false)
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
            }
        }

    }

    fun getBeniDetails(baniid: Int?) {

        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val result = loginServerRepository.getBeniDetails(baniid)
            withContext(Dispatchers.Main) {
                try {

                    benificiaryListResponse.postValue(result.body())
                    loading.postValue(false)
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
            }
        }

    }


    fun Addbeni(addBeniRequest: AddBeniRequest) {

        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val result = loginServerRepository.Addbeni(addBeniRequest)
            withContext(Dispatchers.Main) {
                try {

                    addBeniResponse.postValue(result.body())
                    loading.postValue(false)
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
            }
        }

    }

    fun getAbhaToken() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var response = loginServerRepository.getAbhaToken();
            withContext(Dispatchers.Main) {
                try {


                    abhatoken.postValue(response.body())
                } catch (throwable: Throwable) {
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