/*************************************************
 * Created by Efendi Hariyadi on 04/08/22, 12:55 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 04/08/22, 12:55 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.medicalreport.smartreportresponse

import java.io.Serializable

data class Data(
    val __v: Int,
    val _id: String,
    val mraiId: Int,
    val recordId: Int,
    val testname: String,
    val testunit: String,
    val testvalue: String,
    val dated: String,
    val vitalId:Int,

    val `vitaldetails`: List<Vitaldetails>
):Serializable