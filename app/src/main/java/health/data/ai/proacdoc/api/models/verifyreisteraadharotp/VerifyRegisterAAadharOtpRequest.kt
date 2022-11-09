/*************************************************
 * Created by Efendi Hariyadi on 07/11/22, 2:50 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 07/11/22, 2:50 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.verifyreisteraadharotp

import com.google.gson.annotations.SerializedName

class VerifyRegisterAAadharOtpRequest(
    @SerializedName("otp") var otp: String? = null,
    @SerializedName("txnId") var txnId: String? = null,

)