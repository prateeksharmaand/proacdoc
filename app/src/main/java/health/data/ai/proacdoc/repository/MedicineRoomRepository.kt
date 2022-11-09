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

package health.data.ai.proacdoc.repository

import health.data.ai.proacdoc.room.dao.MedicineDAO
import health.data.ai.proacdoc.room.dao.MedicineTimingDAO
import health.data.ai.proacdoc.room.entity.MedicineEntity
import health.data.ai.proacdoc.room.entity.MedicineTimingsEntity


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