

package health.data.ai.proacdoc.api.models.login

data class LoginResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: health.data.ai.proacdoc.api.models.login.Results
)