/*************************************************
 * Created by Efendi Hariyadi on 04/08/22, 12:55 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 04/08/22, 12:55 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.medicalreport.smartreportresponse

import java.io.Serializable

data class Vitaldetails(

    val _id: String,
    val vitalId: Int,
    val normalizedText: String,
    val normalvalues: String,
    val description: String,
):Serializable