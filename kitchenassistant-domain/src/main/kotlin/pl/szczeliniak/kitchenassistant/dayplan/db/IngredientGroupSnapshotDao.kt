package pl.szczeliniak.kitchenassistant.dayplan.db

interface IngredientGroupSnapshotDao {

    fun save(ingredientGroup: IngredientGroupSnapshot): IngredientGroupSnapshot

}