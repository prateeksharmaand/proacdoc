/*************************************************
 * Created by Efendi Hariyadi on 25/11/22, 12:00 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 25/11/22, 12:00 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.abhaconsentdetails

data class Authorization(
    val authMode: String,
    val createdAt: String,
    val lastUpdated: String,
    val patientId: String,
    val purpose: PurposeX,
    val requestId: String,
    val requester: RequesterX,
    val status: String
)