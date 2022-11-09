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
data class MedicineEntity(
    @PrimaryKey
    @SerializedName("id") var Id: Int? = null,
    @SerializedName("medicineName") var medicineName: String? = null,
    @SerializedName("MedicineType") var MedicineType: String? = null,
    @SerializedName("Strength") var Strength: Int? = null,
    @SerializedName("StrengthUnit") var StrengthUnit: String? = null,
    @SerializedName("Frequency") var Frequency: Int? = null,
    @SerializedName("FrequencyDuration") var FrequencyDuration: String? = null,
    @SerializedName("StartDate") var StartDate: String? = null,
    @SerializedName("Timings") var Timings: String? = null,
    @SerializedName("Shape") var Shape: Int? = null,
    @SerializedName("Color") var Color: String? = null,
    @SerializedName("Isfavourite") var Isfavourite: Boolean? = null,
    @SerializedName("Status") var Status: Boolean? = null,
    @SerializedName("LastUpdatedDate") var LastUpdatedDate: String? = null,
) : Serializable
