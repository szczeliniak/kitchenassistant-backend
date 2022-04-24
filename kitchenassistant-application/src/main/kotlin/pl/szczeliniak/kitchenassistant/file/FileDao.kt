package pl.szczeliniak.kitchenassistant.file

interface FileDao {

    fun save(file: File): File

    fun findById(id: Int): File?

}