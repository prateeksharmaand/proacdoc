/*************************************************
 * Created by Efendi Hariyadi on 18/08/22, 5:25 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 18/08/22, 5:25 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.smarthealthresponse

import java.io.Serializable

data class Data(
    val _id: String,
    var `data`: List<DataX>,
    var outofRange: Int=0,
    var notmalRange: Int=0
):Serializable