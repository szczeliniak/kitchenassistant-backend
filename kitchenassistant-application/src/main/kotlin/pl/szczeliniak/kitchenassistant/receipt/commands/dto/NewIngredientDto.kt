package pl.szczeliniak.kitchenassistant.receipt.commands.dto

import pl.szczeliniak.kitchenassistant.enums.IngredientUnit

data class NewIngredientDto(
    var name: String = "",
    var quantity: String = "",
    var unit: IngredientUnit? = null
)