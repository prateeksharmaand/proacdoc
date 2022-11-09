/*************************************************
 * Created by Efendi Hariyadi on 16/09/22, 10:05 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 16/09/22, 10:05 AM
 ************************************************/

package health.care.ai.api.models.labordersummary

data class LeadHistoryMaster(
    val appointOn: List<AppointOn>,
    val assignTspOn: List<AssignTspOn>,
    val bookedOn: List<BookedOn>,
    val deliverdOn: List<Any>,
    val reappointOn: List<Any>,
    val rejectedOn: Any,
    val reportedOn: List<ReportedOn>,
    val servicedOn: List<ServicedOn>
)