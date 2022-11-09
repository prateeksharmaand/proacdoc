/*************************************************
 * Created by Efendi Hariyadi on 12/09/22, 5:50 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 12/09/22, 5:50 PM
 ************************************************/

package health.data.ai.proacdoc.api.models.benificiary.get

data class Data(
    val __v: Int,
    val _id: String,
    val age: Int,
    val baniid: Int,
    val beniUserId: Int,
    val beniname: String,
    val gender: String,
    var isSelected: Boolean,
    var abhaid: String,
    var abhanumber: String,
    var qrurl: String,
    var token: String,
    var refreshToken: String,
    var lastname: String,


)