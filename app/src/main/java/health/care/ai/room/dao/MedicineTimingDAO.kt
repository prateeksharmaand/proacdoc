/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 5:04 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 5:04 PM
 ************************************************/

package health.care.ai.room.dao


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import health.care.ai.room.entity.MedicineEntity
import health.care.ai.room.entity.MedicineTimingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineTimingDAO {
    @Insert
    suspend fun AddMedicineTiming(medicineEntity: MedicineTimingsEntity?)


    @Query("DELETE FROM MedicineTimingsEntity  WHERE id =:id")
    fun deleteMedicineTiming( id: Int?)


    @Query("SELECT * FROM MedicineTimingsEntity WHERE medicineId =:medicineId")
    fun getTimingsById(medicineId: Int?): Flow<List<MedicineTimingsEntity>>





}