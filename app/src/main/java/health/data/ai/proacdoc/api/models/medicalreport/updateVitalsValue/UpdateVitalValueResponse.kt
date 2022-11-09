/*************************************************
 * Created by Efendi Hariyadi on 16/08/22, 1:04 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 16/08/22, 1:04 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.medicalreport.updateVitalsValue

data class UpdateVitalValueResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)