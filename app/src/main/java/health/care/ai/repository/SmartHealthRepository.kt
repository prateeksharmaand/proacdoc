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

class SmartHealthRepository(private val postsServices: PostsServices) {
    suspend fun getsmarthealthdetails( userId: Int?)=postsServices.getsmarthealthdetails(userId)
}