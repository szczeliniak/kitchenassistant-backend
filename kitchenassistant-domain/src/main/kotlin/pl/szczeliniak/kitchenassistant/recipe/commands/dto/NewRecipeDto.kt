package pl.szczeliniak.kitchenassistant.recipe.commands.dto

import org.hibernate.validator.constraints.Length
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class NewRecipeDto(
        @field:Min(1) var userId: Int = 0,
        @field:Length(min = 1, max = 100) var name: String = "",
        var categoryId: Int? = null,
        @field:Length(max = 1000) var description: String? = null,
        @field:Length(max = 50) var author: String? = null,
        @field:Length(max = 100) var source: String? = null,
        @field:Length(max = 100) var photoName: String? = null,
        @field:Size(min = 0, max = 30) var ingredientGroups: Set<@Valid NewIngredientGroupDto> = setOf(),
        @field:Size(min = 0, max = 30) var steps: Set<@Valid NewStepDto> = setOf(),
        @field:Size(min = 0, max = 30) var tags: Set<@NotNull String> = setOf()
)