/*************************************************
 * Created by Efendi Hariyadi on 08/09/22, 8:01 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 08/09/22, 8:01 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.labtest.login.ProfileTest

import java.io.Serializable

data class Testlist(
    val code: String,
    val groupName: String,
    val name: String
): Serializable