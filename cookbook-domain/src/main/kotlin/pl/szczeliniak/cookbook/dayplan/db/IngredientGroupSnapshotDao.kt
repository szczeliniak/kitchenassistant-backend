package pl.szczeliniak.cookbook.dayplan.db

interface IngredientGroupSnapshotDao {

    fun save(ingredientGroup: IngredientGroupSnapshot): IngredientGroupSnapshot

}