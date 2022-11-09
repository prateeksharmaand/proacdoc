
package health.care.ai.api.models.login.profile

data class UpdateProfileResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: health.care.ai.api.models.login.profile.Results
)