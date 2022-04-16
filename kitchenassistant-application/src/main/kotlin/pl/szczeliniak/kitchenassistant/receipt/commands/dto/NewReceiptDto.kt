package pl.szczeliniak.kitchenassistant.receipt.commands.dto

data class NewReceiptDto(
    var userId: Int = 0,
    var name: String = "",
    var categoryId: Int? = null,
    var description: String? = null,
    var author: String? = null,
    var source: String? = null,
    var ingredients: List<NewIngredientDto> = listOf(),
    var steps: List<NewStepDto> = listOf(),
    var photos: List<String> = listOf()
)