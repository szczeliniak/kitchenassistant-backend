package pl.szczeliniak.kitchenassistant.recipe.db

interface StepDao {

    fun save(step: Step): Step

    fun delete(step: Step)

}