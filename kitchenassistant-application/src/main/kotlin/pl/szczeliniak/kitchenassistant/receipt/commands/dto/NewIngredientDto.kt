package pl.szczeliniak.kitchenassistant.receipt.commands.dto

data class NewIngredientDto(
    var name: String = "",
    var quantity: String = ""
)