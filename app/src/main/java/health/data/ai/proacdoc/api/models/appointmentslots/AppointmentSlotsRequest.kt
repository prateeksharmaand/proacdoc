/*************************************************
 * Created by Efendi Hariyadi on 12/09/22, 2:11 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 12/09/22, 2:11 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.appointmentslots

import com.google.gson.annotations.SerializedName

class AppointmentSlotsRequest(

    @SerializedName("Pincode") var Pincode: String? = null,

    @SerializedName("apiKey") var ApiKey: String? = null,

    @SerializedName("date") var Date: String? = null,
    @SerializedName("providerId") var ProviderId: String? = null,
)