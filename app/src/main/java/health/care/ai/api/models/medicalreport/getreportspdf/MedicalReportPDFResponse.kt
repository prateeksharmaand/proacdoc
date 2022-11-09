/*************************************************
 * Created by Efendi Hariyadi on 28/07/22, 4:41 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 28/07/22, 4:41 PM
 ************************************************/

package health.care.ai.api.models.medicalreport.getreportspdf

data class MedicalReportPDFResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)