/*************************************************
 * Created by Efendi Hariyadi on 07/09/22, 11:50 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 07/09/22, 11:50 AM
 ************************************************/

package health.data.ai.proacdoc.api.models.vitalsbymajorvitalid

data class Data(
    val description: String,
    val majorVitalId: Int,
    val normalizedText: String,
    val normalvalues: String,
    val vitalId: Int
)