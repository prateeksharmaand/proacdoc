

package health.care.ai.api.models.login

data class LoginResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: health.care.ai.api.models.login.Results
)