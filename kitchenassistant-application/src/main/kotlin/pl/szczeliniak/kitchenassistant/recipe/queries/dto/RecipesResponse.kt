package pl.szczeliniak.kitchenassistant.recipe.queries.dto

import pl.szczeliniak.kitchenassistant.shared.dtos.Pagination

data class RecipesResponse(
    val recipes: List<RecipeDto>,
    val pagination: Pagination
)