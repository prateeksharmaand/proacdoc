/*************************************************
 * Created by Efendi Hariyadi on 09/09/22, 2:19 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 09/09/22, 2:19 PM
 ************************************************/

package health.care.ai.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class MedicineTimingsEntity(
    @PrimaryKey
    @SerializedName("id") var Id: Int? = null,
    @SerializedName("medicineId") var medicineId: Int? = null,

) : Serializable
