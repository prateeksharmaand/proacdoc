/*************************************************
 * Created by Efendi Hariyadi on 24/11/22, 5:51 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 24/11/22, 5:51 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.abhaconsentdetails

import com.google.gson.annotations.SerializedName

data class LocalConsentRequest (

    @SerializedName("Providername") var Providername: String? = null,
    @SerializedName("RequestType") var RequestType: String? = null,
    @SerializedName("Perpose") var Perpose: String? = null,
    @SerializedName("Period") var Period: String? = null,
    @SerializedName("Status") var Status: String? = null,


    )