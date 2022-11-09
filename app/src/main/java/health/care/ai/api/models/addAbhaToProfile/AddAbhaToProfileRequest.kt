/*************************************************
 * Created by Efendi Hariyadi on 08/11/22, 5:09 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 08/11/22, 5:09 PM
 ************************************************/

package health.care.ai.api.models.addAbhaToProfile

import com.google.gson.annotations.SerializedName

data class AddAbhaToProfileRequest(
    @SerializedName("baniid") var baniid: Int? = null,

    @SerializedName("abhaid") var abhaid: String? = null,

    @SerializedName("abhanumber") var abhanumber: String? = null,
    @SerializedName("token") var token: String? = null,
    @SerializedName("refreshToken") var refreshToken: String? = null,

)