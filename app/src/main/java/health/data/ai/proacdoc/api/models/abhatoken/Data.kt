/*************************************************
 * Created by Efendi Hariyadi on 07/11/22, 11:37 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 07/11/22, 11:37 AM
 ************************************************/

package health.data.ai.proacdoc.api.models.abhatoken

data class Data(
    val accessToken: String,
    val expiresIn: Int,
    val refreshExpiresIn: Int,
    val refreshToken: String,
    val tokenType: String
)