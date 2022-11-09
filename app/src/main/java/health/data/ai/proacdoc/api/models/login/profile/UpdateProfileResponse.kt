
package health.data.ai.proacdoc.api.models.login.profile

data class UpdateProfileResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: health.data.ai.proacdoc.api.models.login.profile.Results
)