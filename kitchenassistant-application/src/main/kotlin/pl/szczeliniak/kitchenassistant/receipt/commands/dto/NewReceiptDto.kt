package pl.szczeliniak.kitchenassistant.receipt.commands.dto

data class NewReceiptDto(
    var userId: Int = 0,
    var name: String = "",
    var description: String = "",
    var author: String = "",
    var source: String = "",
    var ingredients: List<NewIngredientDto> = ArrayList(),
    var steps: List<NewStepDto> = ArrayList()
)