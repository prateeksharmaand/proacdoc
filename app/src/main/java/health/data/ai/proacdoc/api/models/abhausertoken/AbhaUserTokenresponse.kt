/*************************************************
 * Created by Efendi Hariyadi on 09/11/22, 7:55 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 09/11/22, 7:55 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.abhausertoken

data class AbhaUserTokenresponse(
    val accessToken: String,
    val expiresIn: Int
)