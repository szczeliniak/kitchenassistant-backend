package pl.szczeliniak.kitchenassistant.receipt.queries.dto

import pl.szczeliniak.kitchenassistant.receipt.Photo

data class PhotoDto(
    val id: Int,
    val fileId: Int
) {

    companion object {
        fun fromDomain(photo: Photo): PhotoDto {
            return PhotoDto(photo.id, photo.fileId)
        }
    }
}