package pl.szczeliniak.kitchenassistant.dayplan.db

interface StepGroupSnapshotDao {

    fun save(stepGroupSnapshot: StepGroupSnapshot): StepGroupSnapshot

}