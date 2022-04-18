package pl.szczeliniak.kitchenassistant.receipt

interface FileDao {

    fun save(file: File): File

    fun findById(id: Int): File?

}