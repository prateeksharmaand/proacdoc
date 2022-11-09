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

package health.data.ai.proacdoc.room.dao


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import health.data.ai.proacdoc.room.entity.MedicineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDAO {
    @Insert
    suspend fun AddMedicine(medicineEntity: MedicineEntity?)


    @Query("DELETE FROM MedicineEntity")
    fun deleteMedicine()


    @Query("SELECT * FROM MedicineEntity WHERE Isfavourite =:Isfavourite")
    fun getAllFavouriteMedicines(Isfavourite: Boolean?): Flow<List<MedicineEntity>>

    @Query("UPDATE MedicineEntity SET Status = :Status, LastUpdatedDate = :LastUpdatedDate WHERE id =:id")
    fun update(Status: Boolean?, LastUpdatedDate: String?, id: Int?)

    @Query("UPDATE MedicineEntity SET Isfavourite =:Isfavourite WHERE id =:id")
    fun updateFavourite(Isfavourite: Boolean?, id: Int?)
    @Delete
    fun delete(model: MedicineEntity)



    @Query("SELECT * FROM MedicineEntity")
    fun getAllMedicines(): Flow<List<MedicineEntity>>

}