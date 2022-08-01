package pl.szczeliniak.kitchenassistant.recipe

interface StepDao {

    fun save(step: Step): Step

}