/*************************************************
 * Created by Efendi Hariyadi on 13/09/22, 2:48 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 13/09/22, 2:48 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.benificiary.add

data class AddBeniResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)