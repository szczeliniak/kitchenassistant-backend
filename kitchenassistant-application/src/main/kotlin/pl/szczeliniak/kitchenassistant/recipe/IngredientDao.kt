package pl.szczeliniak.kitchenassistant.recipe

interface IngredientDao {

    fun save(ingredient: Ingredient): Ingredient

}