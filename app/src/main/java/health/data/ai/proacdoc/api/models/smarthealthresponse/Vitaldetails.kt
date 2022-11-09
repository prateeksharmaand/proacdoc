/*************************************************
 * Created by Efendi Hariyadi on 18/08/22, 5:25 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 18/08/22, 5:25 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.smarthealthresponse

import java.io.Serializable

data class Vitaldetails(
    val __v: Int,
    val _id: String,
    val description: String,
    val majorVitalId: Int,
    var medicalrecord: List<Medicalrecord>,
    val normalizedText: String,
    val normalvalues: String,
    val vitalId: Int
): Serializable