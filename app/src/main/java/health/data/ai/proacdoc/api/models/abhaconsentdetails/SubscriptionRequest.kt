/*************************************************
 * Created by Efendi Hariyadi on 25/11/22, 12:28 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 25/11/22, 12:28 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.abhaconsentdetails

data class SubscriptionRequest(
    val categories: List<String>,
    val createdAt: String,
    val hips: Any,
    val hiu: HiuXX,
    val id: String,
    val lastUpdated: String,
    val patient: PatientXX,
    val period: PeriodX,
    val purpose: PurposeXXX,
    val requesterType: String,
    val status: String,
    val subscriptionId: Any
)