/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 05/07/22, 1:26 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 05/07/22, 1:26 PM
 ************************************************/

package health.care.ai.repository

import health.care.ai.api.PostsServices
import health.care.ai.api.models.addAbhaToProfile.AddAbhaToProfileRequest
import health.care.ai.api.models.addAbhaToProfile.AddAbhaToProfileResponse
import health.care.ai.api.models.registerabha.RegisterAbhaRequest
import health.care.ai.api.models.registerabha.RegisterAbhaResponse
import health.care.ai.api.models.registerabhagenerateadharotp.RegisterAbhaGenerateAdharOtpRequest
import health.care.ai.api.models.registerabhageneratemobileotp.RegisterAbhaGenerateMobileOtp
import health.care.ai.api.models.registerabhageneratemobileotp.RegisterabhaGenerateMobileOtpResponse
import health.care.ai.api.models.registerabhaverifymobileotp.RegisterAdharVerifyMobileOtpResponse
import health.care.ai.api.models.registerabhaverifymobileotp.RegisterabhaVerifyMobileOtpRequest
import health.care.ai.api.models.verifyreisteraadharotp.VerifyRegisterAAadharOtpRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

class AbhaRepository(private val abhaServices: PostsServices) {
    suspend fun generateRegisterAdharOtp(
        registerAbhaGenerateAdharOtpRequest: RegisterAbhaGenerateAdharOtpRequest,
        token: String
    ) = abhaServices.generateRegisterAdharOtp(registerAbhaGenerateAdharOtpRequest, token)


    suspend fun verifyRegisterAdharOtp(
        verifyRegisterAAadharOtpRequest: VerifyRegisterAAadharOtpRequest,
        token: String
    ) = abhaServices.verifyRegisterAdharOtp(verifyRegisterAAadharOtpRequest, token)


    suspend fun generateRegisterMobileOtp(
        registerAbhaGenerateMobileOtp: RegisterAbhaGenerateMobileOtp,
        token: String
    ) = abhaServices.generateRegisterMobileOtp(registerAbhaGenerateMobileOtp, token)

    suspend fun verifyRegisterMobileOtp(
        registerabhaVerifyMobileOtpRequest: RegisterabhaVerifyMobileOtpRequest,
        token: String
    ) = abhaServices.verifyRegisterMobileOtp(registerabhaVerifyMobileOtpRequest, token)

    suspend fun createAbhaId(registerAbhaRequest: RegisterAbhaRequest, token: String) =
        abhaServices.createAbhaId(registerAbhaRequest, token)


    suspend fun addAbhaToBeni(

        registerAbhaRequest : AddAbhaToProfileRequest

        ) = abhaServices.addAbhaToBeni(
        registerAbhaRequest
    )
}