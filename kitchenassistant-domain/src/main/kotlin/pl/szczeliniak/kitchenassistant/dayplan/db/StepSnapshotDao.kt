package pl.szczeliniak.kitchenassistant.dayplan.db

interface StepSnapshotDao {

    fun save(step: StepSnapshot): StepSnapshot

}