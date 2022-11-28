/*************************************************
 * Created by Efendi Hariyadi on 25/11/22, 1:02 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 25/11/22, 1:02 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.abhaconsentdetails

data class AuthorisationRequest(
    val authMode: String,
    val createdAt: String,
    val lastUpdated: String,
    val patientId: String,
    val purpose: PurposeXXXX,
    val requestId: String,
    val requester: RequesterXX,
    val status: String
)