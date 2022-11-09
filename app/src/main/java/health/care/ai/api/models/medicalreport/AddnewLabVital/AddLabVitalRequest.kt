

package health.care.ai.api.models.medicalreport.AddnewLabVital

import com.google.gson.annotations.SerializedName

class AddLabVitalRequest(
    @SerializedName("userId") var UserId: Int? = null,
    @SerializedName("recordId") var recordId: Int? = null,
    @SerializedName("data") var data: String? = null,
    @SerializedName("Dated") var Dated: String? = null,
)

