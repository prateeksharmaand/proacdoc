/*************************************************
 * Created by Efendi Hariyadi on 07/11/22, 4:54 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 07/11/22, 4:54 PM
 ************************************************/

package health.care.ai.api.models.registerabhaverifymobileotp

import com.google.gson.annotations.SerializedName

data class RegisterabhaVerifyMobileOtpRequest(

    @SerializedName("otp") var otp: String? = null,
    @SerializedName("txnId") var txnId: String? = null,
)