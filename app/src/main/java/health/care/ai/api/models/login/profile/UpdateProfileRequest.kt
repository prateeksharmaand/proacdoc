
package health.care.ai.api.models.login.profile

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("userId") var UserId: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("image") var image: String? = null,
)