/*************************************************
 * Created by Efendi Hariyadi on 16/09/22, 10:05 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 16/09/22, 10:05 AM
 ************************************************/

package health.data.ai.proacdoc.api.models.labordersummary

data class OrderMaster(
    val address: String,
    val appointmentId: Any,
    val bookingThrough: String,
    val cancelRemarks: String,
    val cmlt: String,
    val email: String,
    val feedback: String,
    val ids: String,
    val incentive: String,
    val mobile: String,
    val names: String,
    val orderNo: String,
    val patinetId: Any,
    val payType: String,
    val pincode: String,
    val products: String,
    val rate: String,
    val refByDRName: String,
    val remarks: String,
    val serviceType: String,
    val status: String,
    val tsp: String,
    val ulc: String
)