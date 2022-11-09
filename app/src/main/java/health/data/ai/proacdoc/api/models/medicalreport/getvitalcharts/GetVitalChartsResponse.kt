/*************************************************
 * Created by Efendi Hariyadi on 16/08/22, 11:33 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 16/08/22, 11:33 AM
 ************************************************/

package health.data.ai.proacdoc.api.models.medicalreport.getvitalcharts

data class GetVitalChartsResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)