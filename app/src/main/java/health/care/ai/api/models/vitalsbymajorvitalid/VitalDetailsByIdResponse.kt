/*************************************************
 * Created by Efendi Hariyadi on 07/09/22, 11:50 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 07/09/22, 11:50 AM
 ************************************************/

package health.care.ai.api.models.vitalsbymajorvitalid

data class VitalDetailsByIdResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)