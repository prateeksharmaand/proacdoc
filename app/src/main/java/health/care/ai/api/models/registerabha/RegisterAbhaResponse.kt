/*************************************************
 * Created by Efendi Hariyadi on 07/11/22, 5:49 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 07/11/22, 5:49 PM
 ************************************************/

package health.care.ai.api.models.registerabha

data class RegisterAbhaResponse(
    val authMethods: List<String>,
    val dayOfBirth: String,
    val districtCode: String,
    val districtName: String,
    val email: String,
    val firstName: String,
    val gender: String,
    val healthId: String,
    val healthIdNumber: String,
    val kycPhoto: String,
    val lastName: String,
    val middleName: String,
    val mobile: String,
    val monthOfBirth: String,
    val name: String,
    val new: Boolean,
    val pincode: Any,
    val profilePhoto: String,
    val refreshToken: String,
    val stateCode: String,
    val stateName: String,
    val tags: Tags,
    val token: String,
    val yearOfBirth: String
)