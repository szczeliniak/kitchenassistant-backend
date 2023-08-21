package pl.szczeliniak.kitchenassistant.recipe.dto.response

data class IngredientGroupResponse(
    val ingredientGroup: IngredientGroupDto
) {
    data class IngredientGroupDto(
        val id: Int,
        val name: String,
        val ingredients: Set<IngredientDto>
    ) {
        data class IngredientDto(
            val id: Int,
            val name: String,
            val quantity: String?
        )
    }
}