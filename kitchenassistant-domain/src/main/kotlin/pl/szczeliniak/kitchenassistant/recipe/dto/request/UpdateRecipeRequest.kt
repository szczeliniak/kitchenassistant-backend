package pl.szczeliniak.kitchenassistant.recipe.dto.request

import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class UpdateRecipeRequest(
    @field:Size(min = 1, max = 100) var name: String = "",
    var categoryId: Int? = null,
    @field:Size(max = 1000) var description: String? = null,
    @field:Size(max = 50) var author: String? = null,
    @field:Size(max = 150) var source: String? = null,
    @field:Size(min = 0, max = 10) var tags: List<@NotNull String> = listOf(),
    @field:Size(min = 0, max = 20) var ingredientGroups: List<IngredientGroup> = listOf(),
    @field:Size(min = 0, max = 20) var stepGroups: List<StepGroup> = listOf()
) {

    data class IngredientGroup(
        @field:Size(min = 1, max = 100) var name: String? = null,
        @Min(0) @Max(30) val ingredients: List<@Valid Ingredient> = listOf()
    ) {
        data class Ingredient(
            @field:Size(min = 1, max = 50) var name: String = "",
            @field:Size(min = 1, max = 50) var quantity: String? = null
        )
    }

    data class StepGroup(
        @field:Size(min = 1, max = 100) var name: String? = null,
        @Min(0) @Max(30) val steps: List<@Valid Step> = listOf()
    ) {
        data class Step(
            @field:Size(max = 1000) var description: String,
            var sequence: Int? = null
        )
    }

}