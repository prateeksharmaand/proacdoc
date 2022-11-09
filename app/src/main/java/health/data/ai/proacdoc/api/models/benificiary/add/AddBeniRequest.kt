/*************************************************
 * Created by Efendi Hariyadi on 13/09/22, 2:47 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 13/09/22, 2:47 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.benificiary.add

import com.google.gson.annotations.SerializedName

class AddBeniRequest(


    @SerializedName("age") var Age: String? = null,

    @SerializedName("beniUserId") var BeniUserId: Int? = null,

    @SerializedName("beniname") var Beniname: String? = null,

    @SerializedName("gender") var Gender: String? = null,
    @SerializedName("lastname") var lastname: String? = null,


)