package pl.szczeliniak.kitchenassistant.recipe.db

interface IngredientGroupDao {

    fun save(ingredientGroup: IngredientGroup): IngredientGroup

}