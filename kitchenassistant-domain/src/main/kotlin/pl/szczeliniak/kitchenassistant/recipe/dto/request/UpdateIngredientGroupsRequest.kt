package pl.szczeliniak.kitchenassistant.recipe.dto.request

import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.Size


data class UpdateIngredientGroupsRequest(var ingredientGroups: List<IngredientGroup>) {

    data class IngredientGroup(
        @field:Size(min = 1, max = 100) var name: String? = null,
        @Min(0) @Max(30) val ingredients: Set<@Valid Ingredient> = setOf()
    ) {
        data class Ingredient(
            @field:Size(min = 1, max = 50) var name: String = "",
            @field:Size(min = 1, max = 50) var quantity: String? = null
        )
    }

}