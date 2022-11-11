/*************************************************
 * Created by Efendi Hariyadi on 08/11/22, 5:09 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 08/11/22, 5:09 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.abhausertoken

import com.google.gson.annotations.SerializedName

data class AbhaUserTokenRequest(
    @SerializedName("refreshToken") var refreshToken: String? = null,



)