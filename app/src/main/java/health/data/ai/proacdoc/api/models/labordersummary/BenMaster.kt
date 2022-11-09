/*************************************************
 * Created by Efendi Hariyadi on 16/09/22, 10:05 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 16/09/22, 10:05 AM
 ************************************************/

package health.data.ai.proacdoc.api.models.labordersummary

data class BenMaster(
    val age: String,
    val barcode: String,
    val gender: String,
    val id: String,
    val mobile: String,
    val name: String,
    val reminder: String,
    val status: String,
    var url: String
)