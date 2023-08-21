package pl.szczeliniak.kitchenassistant.recipe.dto.request

import org.hibernate.validator.constraints.Length
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class UpdateIngredientGroupRequest(
    @field:Length(min = 1, max = 100) var name: String = "",
    @Min(0) @Max(100) var ingredients: Set<@Valid UpdateIngredientRequest> = setOf()
) {
    data class UpdateIngredientRequest(
        var id: Int? = null,
        @field:Length(min = 1, max = 100) var name: String = "",
        @field:Length(min = 1, max = 50) var quantity: String? = null,
    )
}