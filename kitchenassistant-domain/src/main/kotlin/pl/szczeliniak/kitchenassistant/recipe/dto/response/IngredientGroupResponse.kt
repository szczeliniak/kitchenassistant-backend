package pl.szczeliniak.kitchenassistant.recipe.dto.response

data class IngredientGroupResponse(
    val ingredientGroup: IngredientGroup
) {
    data class IngredientGroup(
        val id: Int,
        val name: String,
        val ingredients: Set<Ingredient>
    ) {
        data class Ingredient(
            val id: Int,
            val name: String,
            val quantity: String?
        )
    }
}