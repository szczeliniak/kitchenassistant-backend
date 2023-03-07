package pl.szczeliniak.kitchenassistant.recipe.commands.dto

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class AssignPhotosToRecipeDto(
    @field:Size(min = 1, max = 20) var ids: Set<@NotNull Int> = setOf()
)