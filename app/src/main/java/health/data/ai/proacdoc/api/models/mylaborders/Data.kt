/*************************************************
 * Created by Efendi Hariyadi on 15/09/22, 4:40 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 15/09/22, 4:40 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.mylaborders

import java.io.Serializable

data class Data(
    val BenDataXML: String,
    val __v: Int,
    val _id: String,
    val address: String,
    val appointmentDate: String,
    val bookedOn: String,
    val fasting: String,
    val orderId: String,
    val paymentType: String,
    val product: String,
    val providerId: Int,
    val rate: String,
    val serviceType: String,
    val status: String,
    val userId: Int
):Serializable