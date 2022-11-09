/*************************************************
 * Created by Efendi Hariyadi on 16/08/22, 11:33 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 16/08/22, 11:33 AM
 ************************************************/

package health.care.ai.api.models.medicalreport.getvitalcharts

data class Data(
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
    val vitaldetailsschemas: List<Any>
)