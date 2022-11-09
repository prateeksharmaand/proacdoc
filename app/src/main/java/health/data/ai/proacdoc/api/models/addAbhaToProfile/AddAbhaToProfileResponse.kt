/*************************************************
 * Created by Efendi Hariyadi on 08/11/22, 5:09 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 08/11/22, 5:09 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.addAbhaToProfile

data class AddAbhaToProfileResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)