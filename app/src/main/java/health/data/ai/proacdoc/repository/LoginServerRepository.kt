/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 4:18 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/07/22, 4:18 PM
 ************************************************/


package health.data.ai.proacdoc.repository

import health.data.ai.proacdoc.api.PostsServices
import health.data.ai.proacdoc.api.models.benificiary.add.AddBeniRequest

class LoginServerRepository(private val postsServices: PostsServices) {
    suspend fun loginUser(userModel: health.data.ai.proacdoc.api.models.User.UserModel) =
        postsServices.LoginUser(userModel)

    suspend fun updateToken(userId: Int?, Token: String) =
        postsServices.UpdateNotification(userId, Token)

    suspend fun UpdateProfile(updateProfileRequest: health.data.ai.proacdoc.api.models.login.profile.UpdateProfileRequest) =
        postsServices.UpdateProfile(updateProfileRequest)


    suspend fun getBeni(beniUserId: Int?) = postsServices.getBeni(beniUserId)

    suspend fun getBeniDetails(baniid: Int?) = postsServices.getBeniDetails(baniid)


    suspend fun Addbeni(addBeniRequest: AddBeniRequest) = postsServices.Addbeni(addBeniRequest)


    suspend fun UpdatePhoneNumber(userId: Int?, mobile: String) =
        postsServices.UpdatePhoneNumber(userId, mobile)


    suspend fun getAbhaToken() = postsServices.getAbhaToken();
}