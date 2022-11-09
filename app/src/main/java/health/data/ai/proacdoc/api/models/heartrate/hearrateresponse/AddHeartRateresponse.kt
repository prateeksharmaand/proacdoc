

package health.data.ai.proacdoc.api.models.heartrate.hearrateresponse

data class AddHeartRateresponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)