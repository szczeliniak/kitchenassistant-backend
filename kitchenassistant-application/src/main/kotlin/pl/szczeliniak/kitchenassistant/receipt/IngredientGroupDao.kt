package pl.szczeliniak.kitchenassistant.receipt

interface IngredientGroupDao {

    fun save(ingredientGroup: IngredientGroup): IngredientGroup

}