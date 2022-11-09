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

package health.care.ai.ui.medicalrecords

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import health.care.ai.api.models.medicalreport.addpdf.AddMedicalRepordPdfResponse
import health.care.ai.api.models.medicalreport.getreportspdf.MedicalReportPDFResponse

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

class MedicalReportViewModel(
    private val medicalReportsRepository: MedicalReportsRepository,


) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    var pdfUrl = MutableLiveData<String>()
    val addMedicalPdfResponse = MutableLiveData<AddMedicalRepordPdfResponse>()
    val medicalPdfList = MutableLiveData<MedicalReportPDFResponse>()
    var job: Job? = null

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->

    }
    val loading = MutableLiveData<Boolean>()

    fun getUserReports(UserId: Int?) {
        loading.postValue(true)
        viewModelScope.launch {
            medicalReportsRepository.getallMedicalPDFS(
                UserId
            ).catch { e ->
                onError(e.message.toString())

            }.onEach {
                medicalPdfList.postValue(it)
                loading.postValue(false)
            }.collect()
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




 /*   fun getReportDetailsById(userId: Int,postId: Int) {
        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            var post = medicalReportsRepository.GetPostById(userId,postId)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)

                    postList.postValue(post)


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

    }*/
    fun AddMedicalRecordPDF(file: File? ,userId: Int?) {

        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val reqFile = RequestBody.create(MediaType.parse("application/pdf"), file)
            val body = MultipartBody.Part.createFormData(
                "file",
                file?.name, reqFile
            )


            var userIds = RequestBody.create(
                MediaType.parse("text/plain"),
                userId.toString()
            );
            val result = medicalReportsRepository.AddMedicalRecordPDF(body,userIds)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)


                    addMedicalPdfResponse.postValue(result.body())
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
    fun AddMedicalRecordImage(file: File? ,userId: Int?) {

        loading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val reqFile = RequestBody.create(MediaType.parse("application/pdf"), file)
            val body = MultipartBody.Part.createFormData(
                "file",
                file?.name, reqFile
            )


            var userIds = RequestBody.create(
                MediaType.parse("text/plain"),
                userId.toString()
            );
            val result = medicalReportsRepository.AddMedicalRecordImage(body,userIds)
            withContext(Dispatchers.Main) {
                try {
                    loading.postValue(false)


                    addMedicalPdfResponse.postValue(result.body())
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