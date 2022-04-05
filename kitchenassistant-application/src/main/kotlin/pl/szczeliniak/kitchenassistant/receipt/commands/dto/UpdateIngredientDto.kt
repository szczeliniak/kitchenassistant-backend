package pl.szczeliniak.kitchenassistant.receipt.commands.dto

data class UpdateIngredientDto(
    var name: String = "",
    var quantity: String = ""
)