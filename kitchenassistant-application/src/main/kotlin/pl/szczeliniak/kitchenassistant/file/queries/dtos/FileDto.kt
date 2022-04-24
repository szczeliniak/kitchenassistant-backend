package pl.szczeliniak.kitchenassistant.file.queries.dtos

import pl.szczeliniak.kitchenassistant.file.File

data class FileDto(
    val id: Int,
    val name: String
) {

    companion object {
        fun fromDomain(file: File): FileDto {
            return FileDto(
                file.id,
                file.name
            )
        }
    }
}