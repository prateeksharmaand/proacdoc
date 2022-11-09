/*************************************************
 * Created by Efendi Hariyadi on 17/08/22, 3:43 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 17/08/22, 3:43 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.medicalreport.AddnewLabVital

data class AddLabVitalResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)