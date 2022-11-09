/*************************************************
 * Created by Efendi Hariyadi on 12/09/22, 5:50 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 12/09/22, 5:50 PM
 ************************************************/

package health.care.ai.api.models.benificiary.get

data class BenificiaryListResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)