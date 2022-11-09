/*************************************************
 * Created by Efendi Hariyadi on 16/09/22, 10:05 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 16/09/22, 10:05 AM
 ************************************************/

package health.care.ai.api.models.labordersummary

data class LabOrderSummaryResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)