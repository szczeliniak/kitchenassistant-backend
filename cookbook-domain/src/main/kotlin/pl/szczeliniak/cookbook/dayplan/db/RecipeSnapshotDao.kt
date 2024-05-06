package pl.szczeliniak.cookbook.dayplan.db

interface RecipeSnapshotDao {

    fun save(recipe: RecipeSnapshot): RecipeSnapshot

}