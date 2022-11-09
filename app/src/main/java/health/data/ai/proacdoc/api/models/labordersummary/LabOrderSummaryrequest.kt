/*************************************************
 * Created by Efendi Hariyadi on 16/09/22, 10:04 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 16/09/22, 10:04 AM
 ************************************************/

package health.data.ai.proacdoc.api.models.labordersummary

import com.google.gson.annotations.SerializedName

class LabOrderSummaryrequest(
    @SerializedName("apiKey") var ApiKey: String? = null,

    @SerializedName("orderId") var orderId: String? = null,
    @SerializedName("providerId") var ProviderId: Int? = null,


    )