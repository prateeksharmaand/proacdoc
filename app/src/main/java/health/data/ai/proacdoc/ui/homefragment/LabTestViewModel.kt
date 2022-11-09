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


package health.data.ai.proacdoc.ui.homefragment

import androidx.lifecycle.*
import health.data.ai.proacdoc.api.models.Order.PostOrder.OrderRequest
import health.data.ai.proacdoc.api.models.Order.PostOrder.OrderResponse
import health.data.ai.proacdoc.api.models.appointmentslots.AppointmentSlotresponse
import health.data.ai.proacdoc.api.models.appointmentslots.AppointmentSlotsRequest
import health.data.ai.proacdoc.api.models.labordersummary.LabOrderSummaryResponse
import health.data.ai.proacdoc.api.models.labordersummary.LabOrderSummaryrequest
import health.data.ai.proacdoc.api.models.labtest.login.LabTestLoginRespone
import health.data.ai.proacdoc.api.models.labtest.login.ProfileTest.ProfileTestRequest
import health.data.ai.proacdoc.api.models.labtest.login.ProfileTest.ProfileTestResponse
import health.data.ai.proacdoc.api.models.mylaborders.LabOrdersResponse
import health.data.ai.proacdoc.api.models.pincodeavaiblity.PinCodeAvaiblityRequest
import health.data.ai.proacdoc.api.models.pincodeavaiblity.PincodeAvaiblityResponse
import health.data.ai.proacdoc.repository.LabTestsRepository
import health.data.ai.proacdoc.repository.PostsRoomRepository
import health.data.ai.proacdoc.room.entity.Cart

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.io.IOException

class LabTestViewModel(

    private val labTestsRepository: LabTestsRepository,
    private val postsRoomRepository: PostsRoomRepository,

    ) : ViewModel() {
    val errorMessage = MutableLiveData<String>()


    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        // onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()
    val middlevareToken = MutableLiveData<LabTestLoginRespone>()
    val profileTestResponse = MutableLiveData<ProfileTestResponse>()
    val labOrderSummaryResponse = MutableLiveData<LabOrderSummaryResponse>()

    val cartCount = MutableLiveData<Int>()
    val cartItemExists = MutableLiveData<Boolean>()

    val cartItems = MutableLiveData<List<Cart>>()

    val appointmentSlotresponse = MutableLiveData<AppointmentSlotresponse>()
    val pincodeAvaiblityResponse = MutableLiveData<PincodeAvaiblityResponse>()

    val orderResponse = MutableLiveData<OrderResponse>()
    val labOrdersResponse = MutableLiveData<LabOrdersResponse>()
    var totalAmount = MutableLiveData<Int>(0)
    var beniCount = MutableLiveData<Int>(1)
    var amount = MutableLiveData<Int>(0)
    val isReportRequired = MutableLiveData<Int>(75)


    private fun settotalAmount() {
        totalAmount.value = amount.value!!.times(beniCount.value!!) + isReportRequired.value!!

    }

    fun setReportRequired(required: Int) {
        isReportRequired.value = required
        settotalAmount()
    }

    fun setAmount(amounts: Int) {
        amount.value = amounts
        settotalAmount()

    }

    fun setBeniCount(BenCount: Int) {
        beniCount.value = BenCount

        settotalAmount()
    }


    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }


    fun getMiddlewareToken(providerId: Int?) {

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var post = labTestsRepository.getLabTestProviderLogin(providerId)
            withContext(Dispatchers.Main) {
                try {

                    middlevareToken.postValue(post.body())
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


    fun getProfileTests(profileTestRequest: ProfileTestRequest) {
        loading.postValue(true)
        viewModelScope.launch {
            labTestsRepository.getProfileTests(profileTestRequest).catch { e ->
                onError(e.message.toString())

            }.onEach {
                profileTestResponse.postValue(it)
                loading.postValue(false)
            }.collect()
        }
    }

    fun getOrdersSummary(labOrderSummaryrequest: LabOrderSummaryrequest) {
        loading.postValue(true)
        viewModelScope.launch {
            labTestsRepository.getOrdersSummary(labOrderSummaryrequest).catch { e ->
                onError(e.message.toString())

            }.onEach {
                labOrderSummaryResponse.postValue(it)
                loading.postValue(false)
            }.collect()
        }
    }


    fun getTestCount() {

        viewModelScope.launch {
            postsRoomRepository.getcart().catch { e ->
                onError(e.message.toString())

            }.onEach {
                cartCount.postValue(it.size)
                loading.postValue(false)
            }.collect()
        }
    }

    fun getCartItems() {

        viewModelScope.launch {
            postsRoomRepository.getcart().catch { e ->
                onError(e.message.toString())

            }.onEach {
                cartItems.postValue(it)
                loading.postValue(false)
            }.collect()
        }
    }

    fun AddToCart(cart: Cart) {

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            postsRoomRepository.addTest(cart)

        }
    }

    fun removeFromCart(cart: Cart) {

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            postsRoomRepository.delete(cart)

        }
    }

    fun clearCart() {

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            postsRoomRepository.clearCart()

        }
    }

    fun checkIfExistsInCart(code: String) {

        viewModelScope.launch {
            postsRoomRepository.isCartItemExist(code).catch { e ->
                onError(e.message.toString())

            }.onEach {
                cartItemExists.postValue(it)
                loading.postValue(false)
            }.collect()
        }
    }

    fun getAppointmentTime(appointmentSlotsRequest: AppointmentSlotsRequest) {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var post = labTestsRepository.getAppointmentSlots(appointmentSlotsRequest)
            withContext(Dispatchers.Main) {
                try {

                    appointmentSlotresponse.postValue(post.body())
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

    fun verifyPinCodeAvaiblity(pinCodeAvaiblityRequest: PinCodeAvaiblityRequest) {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var post = labTestsRepository.verifyPinCodeAvaiblity(pinCodeAvaiblityRequest)
            withContext(Dispatchers.Main) {
                try {

                    pincodeAvaiblityResponse.postValue(post.body())
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

    fun AddOrder(orderRequest: OrderRequest) {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var post = labTestsRepository.AddOrder(orderRequest)
            withContext(Dispatchers.Main) {
                try {

                    orderResponse.postValue(post.body())
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

    fun getLabtestOrders(userId: Int?) {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var post = labTestsRepository.getLabtestOrders(userId)
            withContext(Dispatchers.Main) {
                try {

                    labOrdersResponse.postValue(post.body())
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