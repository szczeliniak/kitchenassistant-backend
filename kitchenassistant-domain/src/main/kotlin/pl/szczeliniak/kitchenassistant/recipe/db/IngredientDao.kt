package pl.szczeliniak.kitchenassistant.recipe.db

interface IngredientDao {

    fun save(ingredient: Ingredient): Ingredient
    fun delete(ingredient: Ingredient)

}