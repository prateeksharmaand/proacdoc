/*************************************************
 * Created by Efendi Hariyadi on 24/11/22, 5:50 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 24/11/22, 5:50 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.abhaconsentdetails

data class Request(
    val careContexts: Any,
    val createdAt: String,
    val hiTypes: List<String>,
    val hip: Any,
    val hiu: Hiu,
    val id: String,
    val lastUpdated: String,
    val patient: Patient,
    val permission: Permission,
    val purpose: Purpose,
    val requester: Requester,
    val status: String
)