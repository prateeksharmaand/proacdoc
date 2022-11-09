/*************************************************
 * Created by Efendi Hariyadi on 25/07/22, 5:00 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 25/07/22, 5:00 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.medicalreport.addpdf

data class AddMedicalRepordPdfResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)
