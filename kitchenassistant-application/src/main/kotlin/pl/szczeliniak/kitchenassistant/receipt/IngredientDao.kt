package pl.szczeliniak.kitchenassistant.receipt

interface IngredientDao {

    fun save(ingredient: Ingredient): Ingredient

}