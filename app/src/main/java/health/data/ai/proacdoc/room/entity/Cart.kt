/*************************************************
 * Created by Efendi Hariyadi on 09/09/22, 2:19 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 09/09/22, 2:19 PM
 ************************************************/

package health.data.ai.proacdoc.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Cart(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id") var Id: Int? = null,
    @SerializedName("name") var Name: String? = null,
    @SerializedName("code") var Code: String? = null,
    @SerializedName("rate") var Rate: String? = null,
    @SerializedName("totaltests") var TotalTests: String? = null,
    @SerializedName("testlist") var TestList: String? = null,
    ) : Serializable
