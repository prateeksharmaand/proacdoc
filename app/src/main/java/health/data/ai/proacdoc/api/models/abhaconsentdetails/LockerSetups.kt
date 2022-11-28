/*************************************************
 * Created by Efendi Hariyadi on 24/11/22, 5:50 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 24/11/22, 5:50 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.abhaconsentdetails

data class LockerSetups(
    val limit: Int,
    val offset: Int,
    val requests: List<LockerRequest>,
    val size: Int
)