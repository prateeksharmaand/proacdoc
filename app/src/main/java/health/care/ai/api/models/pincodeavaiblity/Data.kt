/*************************************************
 * Created by Efendi Hariyadi on 12/09/22, 2:52 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 12/09/22, 2:52 PM
 ************************************************/

package health.care.ai.api.models.pincodeavaiblity

data class Data(
    val emergencyService: Int,
    val lastMonthsOrders: String,
    val respId: String,
    val response: String,
    val responsePincode: String,
    val status: String
)