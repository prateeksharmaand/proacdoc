

package health.data.ai.proacdoc.api.models.customvitaldetails.response

data class AddCustomVitalResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)