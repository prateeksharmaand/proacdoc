/*************************************************
 * Created by Efendi Hariyadi on 07/11/22, 5:48 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 07/11/22, 5:48 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.registerabha

import com.google.gson.annotations.SerializedName

data class RegisterAbhaRequest(
    @SerializedName("firstName") var firstName: String? = null,
    @SerializedName("name") var name: String? = null,
      @SerializedName("healthId") var healthId: String? = null,
    @SerializedName("lastName") var lastName: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("txnId") var txnId: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("profilePhoto") var profilePhoto: String? = null,
    @SerializedName("middleName") var middleName: String? = null,


    )