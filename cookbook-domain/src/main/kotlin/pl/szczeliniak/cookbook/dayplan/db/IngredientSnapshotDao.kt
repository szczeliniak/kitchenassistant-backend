package pl.szczeliniak.cookbook.dayplan.db

interface IngredientSnapshotDao {

    fun save(ingredient: IngredientSnapshot): IngredientSnapshot

}