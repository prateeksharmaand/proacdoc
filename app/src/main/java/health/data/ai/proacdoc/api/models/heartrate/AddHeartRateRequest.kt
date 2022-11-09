

package health.data.ai.proacdoc.api.models.heartrate

import com.google.gson.annotations.SerializedName

class AddHeartRateRequest(
    @SerializedName("userId") var UserId: Int? = null,
    @SerializedName("dated") var dated: String? = null,
    @SerializedName("testvalue") var testvalue: Int? = null,
)

