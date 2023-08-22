package pl.szczeliniak.kitchenassistant.recipe.dto.request

import org.hibernate.validator.constraints.Length
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class NewRecipeRequest(
    @field:Length(min = 1, max = 100) var name: String = "",
    var categoryId: Int? = null,
    @field:Length(max = 1000) var description: String? = null,
    @field:Length(max = 50) var author: String? = null,
    @field:Length(max = 100) var source: String? = null,
    @field:Length(max = 100) var photoName: String? = null,
    @field:Size(min = 0, max = 30) var ingredientGroups: Set<@Valid NewIngredientGroupRequest> = setOf(),
    @field:Size(min = 0, max = 30) var steps: Set<@Valid NewStepRequest> = setOf(),
    @field:Size(min = 0, max = 30) var tags: Set<@NotNull String> = setOf()
) {
    data class NewIngredientGroupRequest(
        @field:Length(min = 1, max = 100) var name: String = "",
        @Min(0) @Max(100) var ingredients: Set<@Valid NewIngredientRequest> = setOf()
    ) {
        data class NewIngredientRequest(
            @field:Length(min = 1, max = 100) var name: String = "",
            @field:Length(min = 1, max = 50) var quantity: String? = null
        )
    }

    data class NewStepRequest(
        @field:Length(max = 1000) var description: String? = null,
        @field:Length(max = 100) var photoName: String? = null,
        var sequence: Int? = null
    )
}