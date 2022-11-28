/*************************************************
 * Created by Efendi Hariyadi on 25/11/22, 12:00 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 25/11/22, 12:00 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.abhaconsentdetails

data class Subscription(
    val categories: List<String>,
    val createdAt: String,
    val hips: Any,
    val hiu: HiuX,
    val id: String,
    val lastUpdated: String,
    val patient: PatientX,
    val period: Period,
    val purpose: PurposeXX,
    val requesterType: String,
    val status: String,
    val subscriptionId: String
)