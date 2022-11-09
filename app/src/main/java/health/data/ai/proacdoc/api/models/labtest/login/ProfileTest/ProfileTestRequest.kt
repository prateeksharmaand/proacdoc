/*************************************************
 * Created by Efendi Hariyadi on 08/09/22, 8:01 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 08/09/22, 8:01 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.labtest.login.ProfileTest

import com.google.gson.annotations.SerializedName

class ProfileTestRequest(
    @SerializedName("apiKey") var apiKey: String? = null,
    @SerializedName("providerId") var providerId: Int? = null,
    @SerializedName("type") var type: String? = null,
)