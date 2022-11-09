/*************************************************
 * Created by Efendi Hariyadi on 14/09/22, 2:21 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 14/09/22, 2:21 PM
 ************************************************/

package health.care.ai.api.models.Order.PostOrder

import com.google.gson.annotations.SerializedName

class OrderRequest(

    @SerializedName("providerId") var providerId: Int? = null,
    @SerializedName("apiKey") var apiKey: String? = null,
    @SerializedName("Email") var Email: String? = null,
    @SerializedName("Gender") var Gender: String? = null,
    @SerializedName("Product") var Product: String? = null,
    @SerializedName("Mobile") var Mobile: String? = null,
    @SerializedName("Address") var Address: String? = null,
    @SerializedName("ApptDate") var ApptDate: String? = null,
    @SerializedName("Pincode") var Pincode: String? = null,
    @SerializedName("Rate") var Rate: Int? = null,
    @SerializedName("Reports") var Reports: String? = null,
    @SerializedName("BenCount") var BenCount: String? = null,
    @SerializedName("BenDataXML") var BenDataXML: String? = null,
    @SerializedName("ReportCode") var ReportCode: String? = null,
    @SerializedName("userId") var userId: Int? = null,


    )