/*************************************************
 * Created by Efendi Hariyadi on 07/09/22, 2:22 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 07/09/22, 2:22 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.customvitaldetails

import com.google.gson.annotations.SerializedName

class AddCustomVitalsRequest(

    @SerializedName("userId") var UserId: Int? = null,
    @SerializedName("dated") var dated: String? = null,
    @SerializedName("testname") var testname: String? = null,
    @SerializedName("testvalue") var testvalue: Int? = null,

    @SerializedName("testunit") var testunit: String? = null,

    @SerializedName("normalizedText") var normalizedText: String? = null,

    @SerializedName("vitalId") var vitalId: Int? = null,

    @SerializedName("majorVitalId") var majorVitalId: Int? = null

)