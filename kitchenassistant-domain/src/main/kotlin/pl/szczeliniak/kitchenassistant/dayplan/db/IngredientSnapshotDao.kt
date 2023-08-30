package pl.szczeliniak.kitchenassistant.dayplan.db

interface IngredientSnapshotDao {

    fun save(ingredient: IngredientSnapshot): IngredientSnapshot

}