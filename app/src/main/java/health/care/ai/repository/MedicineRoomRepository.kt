/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 5:03 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 5:03 PM
 ************************************************/

package health.care.ai.repository

import androidx.room.Query
import health.care.ai.room.dao.CartDAO
import health.care.ai.room.dao.MedicineDAO
import health.care.ai.room.dao.MedicineTimingDAO
import health.care.ai.room.dao.UserDAO
import health.care.ai.room.entity.Cart
import health.care.ai.room.entity.MedicineEntity
import health.care.ai.room.entity.MedicineTimingsEntity
import kotlinx.coroutines.flow.Flow


class MedicineRoomRepository(
    private val medicineDAO: MedicineDAO,
    private val medicineTimingDAO: MedicineTimingDAO
) {
    suspend fun getAllFavouriteMedicines(Isfavourite: Boolean?) =
        medicineDAO.getAllFavouriteMedicines(Isfavourite);

    suspend fun AddMedicine(medicineEntity: MedicineEntity?) =
        medicineDAO.AddMedicine(medicineEntity)


    suspend fun update(Status: Boolean?, LastUpdatedDate: String?, id: Int?) =
        medicineDAO.update(Status, LastUpdatedDate, id)


    suspend fun updateFavourite(Isfavourite: Boolean?, id: Int?) =
        medicineDAO.updateFavourite(Isfavourite, id)

    suspend fun delete(model: MedicineEntity) =
        medicineDAO.delete(model)


    suspend fun getAllMedicines() =
        medicineDAO.getAllMedicines();


    suspend fun AddMedicineTiming(medicineEntity: MedicineTimingsEntity?) =
        medicineTimingDAO.AddMedicineTiming(medicineEntity);


    suspend fun deleteMedicineTiming(id: Int?) =
        medicineTimingDAO.deleteMedicineTiming(id);



    suspend fun getTimingsById(medicineId: Int?) =
        medicineTimingDAO.getTimingsById(medicineId);



}