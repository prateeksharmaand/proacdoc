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

package health.care.ai.ui.medication

import androidx.lifecycle.*
import health.care.ai.api.models.User.UserModel
import health.care.ai.api.models.benificiary.add.AddBeniRequest
import health.care.ai.api.models.benificiary.add.AddBeniResponse
import health.care.ai.api.models.benificiary.get.BenificiaryListResponse
import health.care.ai.api.models.updatephoneno.UpdatePhoneNoResponse
import health.care.ai.repository.LoginServerRepository
import health.care.ai.repository.MedicineRoomRepository
import health.care.ai.repository.PostsRoomRepository
import health.care.ai.room.entity.MedicineEntity
import health.care.ai.room.entity.MedicineTimingsEntity

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import retrofit2.HttpException
import java.io.IOException

class MedicineViewModel(

    private val medicineRoomRepository: MedicineRoomRepository,


    ) : ViewModel() {


    val errorMessage = MutableLiveData<String>()
    val medicineList = MutableLiveData<List<MedicineEntity>>()
    val medicineAlarmTimingsList = MutableLiveData<List<MedicineTimingsEntity>>()

    val loginStatus = MutableLiveData<Boolean>()


    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }


    fun saveMedicine(medicineEntity: MedicineEntity) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            medicineRoomRepository.AddMedicine(medicineEntity)
        }

    }

    fun AddMedicineTiming(medicineEntity: MedicineTimingsEntity?) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            medicineRoomRepository.AddMedicineTiming(medicineEntity)
        }

    }


    fun updationMedicationTakenStatus(Status: Boolean?, LastUpdatedDate: String?, id: Int?) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            medicineRoomRepository.update(Status, LastUpdatedDate, id)
        }

    }

    fun updateFavourite(Isfavourite: Boolean?, id: Int?) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            medicineRoomRepository.updateFavourite(Isfavourite, id)
        }

    }

    fun delete(model: MedicineEntity) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            medicineRoomRepository.delete(model)
        }

    }
    fun deleteMedicineTiming(id: Int?) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            medicineRoomRepository.deleteMedicineTiming(id)
        }

    }

    suspend fun getallFavouriteMedicines(): Flow<List<MedicineEntity>> =
        medicineRoomRepository.getAllFavouriteMedicines(true)

    fun getAllFavouriteMedicines() {
        loading.postValue(true)
        viewModelScope.launch {
            getallFavouriteMedicines().catch { e ->
                onError(e.message.toString())

            }.onEach {
                medicineList.postValue(it)
                loading.postValue(false)
            }.collect()
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

    suspend fun getallMedicines(): Flow<List<MedicineEntity>> =
        medicineRoomRepository.getAllMedicines()

    fun getAllMedicines() {
        loading.postValue(true)
        viewModelScope.launch {
            getallMedicines().catch { e ->
                onError(e.message.toString())

            }.onEach {
                medicineList.postValue(it)
                loading.postValue(false)
            }.collect()
        }

    }

    suspend fun getallMedicineTimings(medicineId:Int): Flow<List<MedicineTimingsEntity>> =
        medicineRoomRepository.getTimingsById(medicineId)

    fun getMedicineTiming(medicineId:Int) {
        loading.postValue(true)
        viewModelScope.launch {
            getallMedicineTimings(medicineId).catch { e ->
                onError(e.message.toString())

            }.onEach {
                medicineAlarmTimingsList.postValue(it)
                loading.postValue(false)
            }.collect()
        }

    }

}