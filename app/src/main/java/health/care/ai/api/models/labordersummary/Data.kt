/*************************************************
 * Created by Efendi Hariyadi on 16/09/22, 10:05 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 16/09/22, 10:05 AM
 ************************************************/

package health.care.ai.api.models.labordersummary

data class Data(
    val benMaster: List<BenMaster>,
    val collectionCenters: Any,
    val leadHistoryMaster: List<LeadHistoryMaster>,
    val mergedOrderNos: Any,
    val orderMaster: List<OrderMaster>,
    val qr: Any,
    val respId: String,
    val response: String,
    val tspMaster: List<TspMaster>
)