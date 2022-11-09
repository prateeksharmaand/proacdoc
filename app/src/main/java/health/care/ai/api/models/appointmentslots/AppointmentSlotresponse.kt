/*************************************************
 * Created by Efendi Hariyadi on 12/09/22, 2:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 12/09/22, 2:37 PM
 ************************************************/

package health.care.ai.api.models.appointmentslots

data class AppointmentSlotresponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)