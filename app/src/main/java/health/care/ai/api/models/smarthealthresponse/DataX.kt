/*************************************************
 * Created by Efendi Hariyadi on 18/08/22, 5:25 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 18/08/22, 5:25 PM
 ************************************************/

package health.care.ai.api.models.smarthealthresponse

import java.io.Serializable

data class DataX(
    val __v: Int,
    val _id: String,
    val image: String,
    val majorVitalId: Int,
    var medicalrecord: List<Medicalrecord>,
    val normalizedText: String,
    val vitaldetails: Vitaldetails
): Serializable