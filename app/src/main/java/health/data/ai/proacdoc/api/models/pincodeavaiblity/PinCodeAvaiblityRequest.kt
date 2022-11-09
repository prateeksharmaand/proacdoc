/*************************************************
 * Created by Efendi Hariyadi on 12/09/22, 2:45 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 12/09/22, 2:45 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.pincodeavaiblity

import com.google.gson.annotations.SerializedName

class PinCodeAvaiblityRequest(

    @SerializedName("Pincode") var Pincode: String? = null,
    @SerializedName("apiKey") var apiKey: String? = null,
    @SerializedName("providerId") var providerId: String? = null,


    )