/*************************************************
 * Created by Efendi Hariyadi on 07/11/22, 4:48 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 07/11/22, 4:48 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.registerabhageneratemobileotp

import com.google.gson.annotations.SerializedName

data class RegisterabhaGenerateMobileOtpResponse(

    @SerializedName("txnId") var txnId: String? = null,
)