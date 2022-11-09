

package health.data.ai.proacdoc.api.models.Order.PostOrder

data class OrderResponse(
    val code: Int,
    val error: Boolean,
    val message: String,
    val results: Results
)