package pl.szczeliniak.kitchenassistant.receipt

interface StepDao {

    fun save(step: Step): Step

}