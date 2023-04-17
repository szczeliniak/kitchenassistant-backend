package pl.szczeliniak.kitchenassistant.photo.queries.dto

import pl.szczeliniak.kitchenassistant.recipe.SupportedMediaType

data class GetPhotoResponse(val mediaType: SupportedMediaType, val body: ByteArray) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GetPhotoResponse

        if (mediaType != other.mediaType) return false
        return body.contentEquals(other.body)
    }

    override fun hashCode(): Int {
        var result = mediaType.hashCode()
        result = 31 * result + body.contentHashCode()
        return result
    }

}