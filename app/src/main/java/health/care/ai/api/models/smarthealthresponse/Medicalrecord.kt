/*************************************************
 * Created by Efendi Hariyadi on 18/08/22, 5:25 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 18/08/22, 5:25 PM
 ************************************************/

package health.care.ai.api.models.smarthealthresponse

import java.io.Serializable

data class Medicalrecord(
    val __v: Int,
    val _id: String,
    val dated: String,
    val mraiId: Int,
    val normalizedText: String,
    val recordId: Int,
    val testname: String,
    val testunit: String,
    val testvalue: String,
    val userId: Int,
    val vitalId: Int,


): Serializable