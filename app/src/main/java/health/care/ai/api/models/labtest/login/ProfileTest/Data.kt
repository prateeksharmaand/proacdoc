/*************************************************
 * Created by Efendi Hariyadi on 08/09/22, 8:01 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 08/09/22, 8:01 PM
 ************************************************/

package health.care.ai.api.models.labtest.login.ProfileTest

import java.io.Serializable

data class Data(
    val category: String,
    val code: String,
    val discount: String,
    val diseaseGroup: String,
    val fasting: String,
    val groupName: String,
    val image: String,
    val image1: String,
    val name: String,
    val rate: String,
    val testCount: String,
    val testlist: List<Testlist>,
    val units: Any
):Serializable