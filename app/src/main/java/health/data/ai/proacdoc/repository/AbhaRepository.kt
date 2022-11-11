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

package health.data.ai.proacdoc.repository

import health.data.ai.proacdoc.api.PostsServices
import health.data.ai.proacdoc.api.models.abhausertoken.AbhaUserTokenRequest
import health.data.ai.proacdoc.api.models.abhausertoken.AbhaUserTokenresponse
import health.data.ai.proacdoc.api.models.addAbhaToProfile.AddAbhaToProfileRequest
import health.data.ai.proacdoc.api.models.registerabha.RegisterAbhaRequest
import health.data.ai.proacdoc.api.models.registerabhagenerateadharotp.RegisterAbhaGenerateAdharOtpRequest
import health.data.ai.proacdoc.api.models.registerabhageneratemobileotp.RegisterAbhaGenerateMobileOtp
import health.data.ai.proacdoc.api.models.registerabhaverifymobileotp.RegisterabhaVerifyMobileOtpRequest
import health.data.ai.proacdoc.api.models.verifyreisteraadharotp.VerifyRegisterAAadharOtpRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header

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

    suspend fun getAbhaUserToken(abhaUserTokenRequest: AbhaUserTokenRequest, token:String)=abhaServices.getAbhaUserToken(abhaUserTokenRequest,token)
    suspend fun GetAbhaUserQr( token:String,xToken:String,)=abhaServices.GetAbhaUserQr(token,xToken)
    suspend fun GetAbhaUserCard(  token:String,xToken:String,)=abhaServices.GetAbhaUserCard(token,xToken)



}