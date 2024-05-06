package pl.szczeliniak.cookbook.dayplan.db

interface StepSnapshotDao {

    fun save(step: StepSnapshot): StepSnapshot

}