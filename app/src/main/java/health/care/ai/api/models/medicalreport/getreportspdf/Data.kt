/*************************************************
 * Created by Efendi Hariyadi on 28/07/22, 4:41 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 28/07/22, 4:41 PM
 ************************************************/

package health.care.ai.api.models.medicalreport.getreportspdf

data class Data(
    val __v: Int,
    val _id: String,
    val ago: String,
    val dateTimeStamp: String,
    val fileType: Int,
    val fileUrl: String,
    val recordId: Int,
    val smartReport: Int,
    val userId: Int
)