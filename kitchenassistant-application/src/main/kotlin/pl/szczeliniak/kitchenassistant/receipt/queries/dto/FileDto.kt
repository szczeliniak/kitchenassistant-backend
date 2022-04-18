package pl.szczeliniak.kitchenassistant.receipt.queries.dto

import pl.szczeliniak.kitchenassistant.receipt.File

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