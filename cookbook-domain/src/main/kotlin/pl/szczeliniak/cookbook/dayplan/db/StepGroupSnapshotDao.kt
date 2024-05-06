package pl.szczeliniak.cookbook.dayplan.db

interface StepGroupSnapshotDao {

    fun save(stepGroupSnapshot: StepGroupSnapshot): StepGroupSnapshot

}