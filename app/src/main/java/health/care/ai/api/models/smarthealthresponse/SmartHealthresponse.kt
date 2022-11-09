/*************************************************
 * Created by Efendi Hariyadi on 18/08/22, 5:25 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 18/08/22, 5:25 PM
 ************************************************/

package health.care.ai.api.models.smarthealthresponse

data class SmartHealthresponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)