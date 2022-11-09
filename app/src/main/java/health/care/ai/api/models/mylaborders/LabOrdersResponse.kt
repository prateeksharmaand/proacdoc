/*************************************************
 * Created by Efendi Hariyadi on 15/09/22, 4:40 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 15/09/22, 4:40 PM
 ************************************************/

package health.care.ai.api.models.mylaborders

data class LabOrdersResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)