package pl.szczeliniak.kitchenassistant.recipe

interface IngredientGroupDao {

    fun save(ingredientGroup: IngredientGroup): IngredientGroup

}