/*************************************************
 * Created by Efendi Hariyadi on 08/11/22, 1:46 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 08/11/22, 1:46 PM
 ************************************************/

package health.care.ai.api.models.registerabha

data class AbhaExceptionResponse(
    val code: String,
    val details: List<Detail>,
    val message: String
)