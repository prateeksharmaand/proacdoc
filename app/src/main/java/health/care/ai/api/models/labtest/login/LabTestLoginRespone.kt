/*************************************************
 * Created by Efendi Hariyadi on 08/09/22, 7:22 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 08/09/22, 7:22 PM
 ************************************************/

package health.care.ai.api.models.labtest.login

data class LabTestLoginRespone(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)