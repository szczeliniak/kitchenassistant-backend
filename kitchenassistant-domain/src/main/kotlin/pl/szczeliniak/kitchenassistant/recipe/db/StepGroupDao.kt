package pl.szczeliniak.kitchenassistant.recipe.db

interface StepGroupDao {

    fun save(stepGroup: StepGroup): StepGroup
    fun delete(stepGroup: StepGroup)

}