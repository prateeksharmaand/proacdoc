/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 01/07/22, 12:23 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 01/07/22, 12:23 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 11/06/22, 2:19 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 11/06/22, 2:18 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 11/06/22, 2:17 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 11/06/22, 2:17 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.User

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id") var Id: Int? = null,
    @SerializedName("userId") var UserId: Int? = null,
    @SerializedName("emailAddress") var EmailAddress: String? = null,
    @SerializedName("name") var Name: String? = null,
    @SerializedName("latitude") var Latitude: String? = null,
    @SerializedName("longitude") var Longitude: String? = null,
    @SerializedName("image") var Image: String? = null,
    @SerializedName("postId") var postId: Int? = null,
    @SerializedName("mobile") var mobile: String? = null,
) : Serializable
