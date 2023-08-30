package pl.szczeliniak.kitchenassistant.dayplan.db

interface RecipeSnapshotDao {

    fun save(recipe: RecipeSnapshot): RecipeSnapshot

}