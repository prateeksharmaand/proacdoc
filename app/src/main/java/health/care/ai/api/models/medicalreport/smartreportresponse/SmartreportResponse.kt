/*************************************************
 * Created by Efendi Hariyadi on 04/08/22, 12:55 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 04/08/22, 12:55 PM
 ************************************************/

package health.care.ai.api.models.medicalreport.smartreportresponse

data class SmartreportResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)