/*************************************************
 * Created by Efendi Hariyadi on 08/09/22, 8:01 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 08/09/22, 8:01 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.labtest.login.ProfileTest

data class ProfileTestResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)